package ru.softmachine.odyssey.backend.cms.service.storage;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.repository.FieldDefRepository;
import ru.softmachine.odyssey.backend.cms.service.storage.provider.UploadProvider;
import ru.softmachine.odyssey.backend.cms.service.storage.provider.dto.FileInfo;
import ru.softmachine.odyssey.backend.cms.utils.compression.CompressionUtils;
import ru.softmachine.odyssey.backend.cms.utils.FileManagementUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadService {

    private final UploadProvider uploadProvider;
    private final FieldDefRepository fieldDefRepository;

    /**
     * Может использоваться для эффективной организации
     * хранения бинарного содержимого
     */
    @Value("${app.upload.prefix}")
    private String defaultPrefix;

    /**
     * Используется для определения, включено ли сжатие
     */
    @Value("${app.upload.compression.compressionEnabled}")
    private Boolean compressionEnabled;
    @Value("${app.upload.compression.image.compress-min-length}")
    private Long imageCompressSize;
    @Value("${app.upload.compression.video.compress-min-length}")
    private Long videoCompressSize;

    /**
     * Для работы со сжатием
     */
    @Autowired
    private CompressionUtils compressionUtils;
    @Autowired
    private FileManagementUtils fileManagementUtils;


    /**
     * Загружает файл в хранилище, возвращает итоговый идентификатор
     *
     * @return
     */
    @SneakyThrows
    public String uploadFile(String id,
                             UUID fieldDefId,
                             @Nullable String prefix,
                             MultipartFile file
    ) {
        // Полный идентификатор строится как конкатенация префикса и идентификатора
        var fullId = (prefix != null ? prefix : defaultPrefix) + id;
        var fileLength = file.getSize();
        var contentType = file.getContentType();
        var fieldDef = fieldDefRepository.findById(fieldDefId).orElse(null);

        try (var fileStream = new ByteArrayInputStream(file.getBytes())) {
            if (contentType.startsWith("image/")) {
                uploadImage(
                        fullId,
                        contentType,
                        fileStream,
                        fileLength,
                        isImageCompressionNeeded(fieldDef, fileLength, contentType)
                );
            } else if (contentType.startsWith("video/")) {
                uploadVideo(
                        fullId,
                        contentType,
                        fileStream,
                        fileLength,
                        isVideoCompressionNeeded(fieldDef, fileLength, contentType)
                );
            } else {
                throw new RuntimeException("Unsupported content type: " + contentType);
            }

        }

        return fullId;
    }

    /**
     * Скачивает файл из хранилища, возвращает байтовый поток
     *
     * @return
     */
    @SneakyThrows
    public FileInfo download(String id) {
        return uploadProvider.download(id);
    }

    public void uploadImage(String id,
                            String contentType,
                            InputStream in,
                            long length,
                            boolean compress
    ) {
        var outputStream = new ByteArrayOutputStream();
        var fileLength = length;
        var inputStream = in;

        if (compress) {
            log.debug("Compress image {} with length: {}", id, fileLength);
            compressionUtils.compressImage(inputStream, outputStream);
            fileLength = outputStream.size();
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        }

        uploadProvider.upload(id, inputStream, fileLength, contentType);
    }

    public void uploadVideo(String id,
                            String contentType,
                            InputStream in,
                            long length,
                            boolean compress
    ) {
        File inTmpFile = null, outTmpFile = null;
        var fileLength = length;
        var inputStream = in;

        if (compress) {
            log.debug("Compress video {} with length: {}", id, fileLength);
            inTmpFile = fileManagementUtils.createFileFromStream(in, fileManagementUtils.getFile());
            outTmpFile = fileManagementUtils.getFile();
            compressionUtils.compressVideo(inTmpFile, outTmpFile, contentType.substring(contentType.lastIndexOf('/') + 1));

            try {
                inputStream = new FileInputStream(outTmpFile);
                fileLength = outTmpFile.length();
                log.debug("Video with id {} Output length: {}", id, fileLength);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        uploadProvider.upload(id, inputStream, fileLength, contentType);
        fileManagementUtils.removeFile(inTmpFile);
        fileManagementUtils.removeFile(outTmpFile);
    }

    private boolean isImageCompressionNeeded(FieldDef fieldDef, long length, String contentType) {
        var lengthKB = length / 1024;
        var compressCurrentFile = fieldDef.isCompressionEnabled();
        var compressionLimit = fieldDef.getCompressionLimit() != null ?
                fieldDef.getCompressionLimit() : imageCompressSize;

        // Проверяем, включено ли сжатие в настройках, нужно ли сжимать конкретно этот файл
        // и превышает ли размер данного файла заданный порог для сжатия
        return compressionEnabled
                && compressCurrentFile
                && lengthKB >= compressionLimit
                && !contentType.contains("svg");
    }

    private boolean isVideoCompressionNeeded(FieldDef fieldDef, long length, String contentType) {
        var lengthKB = length / 1024;
        var compressCurrentFile = fieldDef.isCompressionEnabled();
        var compressionLimit = fieldDef.getCompressionLimit() != null ?
                fieldDef.getCompressionLimit() : videoCompressSize;

        // Проверяем, включено ли сжатие в настройках, нужно ли сжимать конкретно этот файл
        // и превышает ли размер данного файла заданный порог для сжатия
        return compressionEnabled
                && compressCurrentFile
                && lengthKB >= compressionLimit;
    }
}
