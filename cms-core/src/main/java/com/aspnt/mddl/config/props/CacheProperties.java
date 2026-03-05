package com.aspnt.mddl.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "app.cache")
public class CacheProperties {

    private Boolean enabled;
    private Long globalTtl; // minutes
}
