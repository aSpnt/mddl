package com.aspnt.mddl.dsl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jeasy.random.EasyRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Given {

    private final EasyRandom easyRandom;
    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    @Value("${wiremock.server.url}")
    private String wireMockUrl;


    private <T> T beanOf(Class<T> type) {
        return applicationContext.getBean(type);
    }
}
