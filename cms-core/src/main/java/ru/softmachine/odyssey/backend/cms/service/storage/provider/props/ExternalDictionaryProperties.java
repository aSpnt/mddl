package ru.softmachine.odyssey.backend.cms.service.storage.provider.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties("app")
public class ExternalDictionaryProperties {

    private Map<String, Object> context;

}
