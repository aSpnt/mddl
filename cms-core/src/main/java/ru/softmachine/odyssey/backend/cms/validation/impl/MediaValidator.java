package ru.softmachine.odyssey.backend.cms.validation.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;
import ru.softmachine.odyssey.backend.cms.dto.validation.ValidationType;
import ru.softmachine.odyssey.backend.cms.service.storage.provider.S3UploadProvider;
import ru.softmachine.odyssey.backend.cms.dto.validation.ConstraintViolation;
import ru.softmachine.odyssey.backend.cms.validation.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class MediaValidator implements Validator {

    private final S3UploadProvider s3UploadProvider;
    private final AutoDetectParser parser = new AutoDetectParser();
    private final BodyContentHandler handler = new BodyContentHandler();

    @Override
    public ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext,
            String path) {

        if (fieldValidation.getIntValue() == null && fieldValidation.getTextArrayValue() == null
                || !fieldValue.getFieldDef().getMultiple() && fieldValue.getTextValue() == null) {
            return null;
        }

        var imageLinks = fieldValue.getFieldDef().getMultiple() ?
                fieldValue.getArrayText() :
                List.of(fieldValue.getTextValue());

        return imageLinks.stream()
                .map(imageUrl -> s3UploadProvider.download(imageUrl).getStream())
                .map(imageStream -> {
                    String mediaType;
                    var metadata = new Metadata();

                    try {
                        mediaType = parseInputFile(imageStream, metadata);
                    } catch (RuntimeException e) {
                        return new ConstraintViolation(
                                "Incorrect file format: error while parsing file metadata",
                                path,
                                fieldValidation.getType());
                    }

                    var height = Optional.ofNullable(metadata.get("image_height"))
                            .map(Integer::parseInt)
                            .orElse(null);
                    var width = Optional.ofNullable(metadata.get("image_width"))
                            .map(Integer::parseInt)
                            .orElse(null);
                    var imageSize = Optional.ofNullable(metadata.get("file_size"))
                            .map(Integer::parseInt)
                            .orElse(null);

                    // Обработать форматы вида как image/png, так и image/svg+xml
                    var format = mediaType.split("[/]")[1].split("[+]")[0];

                    var isValid = switch (fieldValidation.getType()) {
                        case MEDIA_FORMAT -> {
                            var validFormat = fieldValidation.getTextArrayValue().stream()
                                    .filter(format::equals)
                                    .findFirst()
                                    .orElse(null);
                            yield validFormat != null;
                        }
                        case MEDIA_MIN_HEIGHT -> height == null || height >= fieldValidation.getIntValue();
                        case MEDIA_MAX_HEIGHT -> height == null || height <= fieldValidation.getIntValue();
                        case MEDIA_MIN_WIDTH -> width == null || width >= fieldValidation.getIntValue();
                        case MEDIA_MAX_WIDTH -> width == null || width <= fieldValidation.getIntValue();
                        case MEDIA_MAX_SIZE ->
                                imageSize == null || (imageSize / 1024) <= fieldValidation.getIntValue();  // Размер в килобайтах
                        default -> {
                            // Если встретился неизвестный валидатор, то не валидируем
                            log.warn("Unsupported validation: {}", fieldValidation.getType());
                            yield true;
                        }
                    };

                    if (!isValid) {
                        return new ConstraintViolation(
                                fieldValidation.getMessage(),
                                path,
                                fieldValidation.getType());
                    }

                    return null;

                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<ValidationType> getValidationTypes() {
        return Set.of(
                ValidationType.MEDIA_FORMAT,
                ValidationType.MEDIA_MAX_SIZE,
                ValidationType.MEDIA_MAX_WIDTH,
                ValidationType.MEDIA_MAX_HEIGHT,
                ValidationType.MEDIA_MIN_WIDTH,
                ValidationType.MEDIA_MIN_HEIGHT
        );
    }

    private String parseInputFile(InputStream stream, Metadata metadata) {
        String mediaType;

        try (TikaInputStream tikaStream = TikaInputStream.get(stream)) {
            var fileLength = tikaStream.getLength();
            parser.parse(tikaStream, handler, metadata);
            prepareMetadata(metadata, fileLength);
            mediaType = metadata.get("Content-Type");

        } catch (IOException | SAXException | TikaException e) {
            throw new RuntimeException(e);
        }

        // Возвращает тип файла в виде: "тип/подтип"
        // Например: "image/jpeg"
        return mediaType;
    }

    private void prepareMetadata(Metadata metadata, long fileLength) {
        // Перебираем различные названия полей метаданных, чтобы найти нужное значение,
        // затем приводим названия этих полей к единому формату
        String[] heightMetadata = {"Exif Image Height", "tiff:ImageLength", "Image Height", "height"};
        String[] widthsMetadata = {"Exif Image Width", "tiff:ImageWidth", "Image Width", "width"};

        var height = Arrays.stream(heightMetadata)
                .filter(fieldName -> (metadata.get(fieldName) != null))
                .map(fieldName -> metadata.get(fieldName).split(" ")[0])
                .findFirst()
                .orElse(null);
        var width = Arrays.stream(widthsMetadata)
                .filter(fieldName -> (metadata.get(fieldName) != null))
                .map(fieldName -> metadata.get(fieldName).split(" ")[0])
                .findFirst()
                .orElse(null);

        // Добавляем File Size в свойства, если его нет в метаданных
        String fileSize;
        if (metadata.get("File Size") == null) {
            fileSize = String.valueOf(fileLength);
        } else {
            fileSize = metadata.get("File Size").split(" ")[0];
        }

        metadata.add("image_height", height);
        metadata.add("image_width", width);
        metadata.add("file_size", fileSize);
    }
}
