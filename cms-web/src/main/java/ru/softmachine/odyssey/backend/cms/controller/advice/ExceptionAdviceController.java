package com.aspnt.mddl.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.aspnt.mddl.exception.CommonExceptionResponse;
import com.aspnt.mddl.exception.EntityNotFoundException;
import com.aspnt.mddl.exception.EntityReferenceException;
import com.aspnt.mddl.exception.OptimisticLockProtectionException;
import com.aspnt.mddl.exception.ValidationException;
import com.aspnt.mddl.dto.validation.ConstraintViolation;

import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class ExceptionAdviceController {

    @ExceptionHandler(OptimisticLockProtectionException.class)
    public ResponseEntity<CommonExceptionResponse> handleException(OptimisticLockProtectionException e) {
        var additionalData = new HashMap<String, Object>();
        additionalData.put("version", e.getVersion());

        return new ResponseEntity<>(
                new CommonExceptionResponse()
                        .setId(e.getId())
                        .setMessage(e.getMessage())
                        .setAdditionalData(additionalData),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonExceptionResponse> handleException(EntityNotFoundException e) {
        return new ResponseEntity<>(
                new CommonExceptionResponse()
                        .setId(e.getId())
                        .setMessage(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(EntityReferenceException.class)
    public ResponseEntity<CommonExceptionResponse> handleException(EntityReferenceException e) {
        return new ResponseEntity<>(
                new CommonExceptionResponse()
                        .setId(e.getId())
                        .setMessage(e.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<List<ConstraintViolation>> handleException(ValidationException e) {
        return new ResponseEntity<>(e.getViolations(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class, UnsupportedOperationException.class})
    public ResponseEntity<CommonExceptionResponse> handleException(Exception e) {
        return new ResponseEntity<>(
                new CommonExceptionResponse().setMessage(e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
