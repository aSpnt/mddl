package ru.softmachine.odyssey.backend.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommonExceptionResponse {

    private String id;
    private String message;
    private Map<String, Object> additionalData;
}

