package com.aspnt.mddl.dto.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConstraintViolation {

    private String message;
    private String path;
    private ValidationType type;
}
