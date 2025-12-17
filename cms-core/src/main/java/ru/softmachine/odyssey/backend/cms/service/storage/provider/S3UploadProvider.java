package ru.softmachine.odyssey.backend.cms.service.storage.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.service.storage.provider.dto.FileInfo;
import ru.softmachine.odyssey.backend.cms.service.storage.provider.props.S3Properties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.net.URI;

/**
 * Реализация хранилища бинарного содержимого на основе S3
 */
@Slf4j
@Component
public class S3UploadProvider implements UploadProvider {

    private final S3Properties s3Properties;

    private final S3Client s3Client;


    public S3UploadProvider(S3Properties s3Properties) {
        this.s3Properties = s3Properties;
        this.s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecret())))
                .endpointOverride(URI.create(s3Properties.getEndpoint()))
                .region(Region.of(s3Properties.getRegion()))
                .build();
    }

    public FileInfo download(String id) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(id)
                .build();

        var responseStream = s3Client.getObject(getObjectRequest);
        var response = responseStream.response();

        return new FileInfo(
                responseStream,
                response.contentLength(),
                response.contentType()
        );
    }

    public boolean exists(String id) {
        try {
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(s3Properties.getBucket())
                    .key(id)
                    .build());
            return true;
        } catch (NoSuchKeyException ignored) {
            return false;
        }
    }

    public void upload(String id, InputStream inputStream, long length, String contentType) {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(s3Properties.getBucket())
                        .key(id)
                        .contentType(contentType)
                        .build(),
                RequestBody.fromContentProvider(() -> inputStream, length, contentType));
        log.info("File {} with type {} and length {} was uploaded", id, contentType, length);
    }

    public void deleteImage(String id) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(id)
                .build());
        log.info("Image {} was deleted", id);
    }
}
