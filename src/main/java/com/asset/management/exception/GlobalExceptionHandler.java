package com.asset.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoAssignedAssetsException.class)
    public ResponseEntity<String> handleNoAssignedAssetsException(NoAssignedAssetsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }
}
