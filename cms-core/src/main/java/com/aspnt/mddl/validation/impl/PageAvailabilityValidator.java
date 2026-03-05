package com.aspnt.mddl.validation.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.aspnt.mddl.dto.FieldValueDto;
import com.aspnt.mddl.entity.validation.FieldValidation;
import com.aspnt.mddl.dto.validation.ValidationType;
import com.aspnt.mddl.dto.validation.ConstraintViolation;
import com.aspnt.mddl.validation.Validator;

import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class PageAvailabilityValidator implements Validator {

    @Override
    public ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext, String path
    ) {
        var url = fieldValue.getTextValue();
        var client = WebClient.create(url);

        if (StringUtils.isBlank(url)) {
            log.warn("Url is null, aborting validation. Path: {}", path);
            return null;
        }

        HttpStatusCode result;
        try {
            result = client.head()
                    .exchangeToMono(response -> Mono.just(response.statusCode()))
                    .onErrorResume(e -> Mono.empty())  // При любой ошибке вернет `null`
                    .block();
        } catch (Exception e) {
            log.warn("Exception while parsing url, aborting validation. Path: {}", path);
            return null;
        }

        if (result == null || result.is4xxClientError() || result.is5xxServerError()) {
            return new ConstraintViolation(
                    fieldValidation.getMessage(),
                    path,
                    ValidationType.URL_PRESENT);
        } else {
            log.warn("Given url {} is not available or does not exist: {}", url, result);
        }

        return null;
    }

    @Override
    public Set<ValidationType> getValidationTypes() {
        return Set.of(ValidationType.URL_PRESENT);
    }
}
