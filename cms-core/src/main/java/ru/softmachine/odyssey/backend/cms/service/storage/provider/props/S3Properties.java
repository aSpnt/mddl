package ru.softmachine.odyssey.backend.cms.service.storage.provider.props;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.upload.provider.s3")
@ConditionalOnProperty(prefix = "app.upload.provider", name = "s3", matchIfMissing = true)
public class S3Properties {

    private String endpoint;
    private String bucket;
    private String accessKey;
    private String secret;
    private String region;
}
