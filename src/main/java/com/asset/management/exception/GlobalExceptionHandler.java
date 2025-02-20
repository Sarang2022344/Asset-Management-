package com.asset.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AssetAlreadyDisposedException.class)
    public ResponseEntity<Map<String, Object>> handleAssetAlreadyDisposedException(AssetAlreadyDisposedException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildErrorResponse("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidRequestException(InvalidRequestException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAssignedAssetsException.class)
    public ResponseEntity<String> handleNoAssignedAssetsException(NoAssignedAssetsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(InvalidAssetException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidAssetException(InvalidAssetException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AssetNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAssetNotFoundException(AssetNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(CSVProcessingException.class)
//    public ResponseEntity<Map<String, Object>> handleCSVProcessingException(CSVProcessingException ex) {
//        return buildErrorResponse("Error processing CSV: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(CSVProcessingException.class)
    public ResponseEntity<Map<String, Object>> handleCSVProcessingException(CSVProcessingException ex) {
        return buildErrorResponse("Error processing CSV: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }

}
