package com.novig.agency_management_system.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        // Check if the exception is due to a foreign key constraint violation
        if (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
            String errorMessage = "Cannot delete the shop because it is referenced in existing sale invoices.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // Handle other types of DataIntegrityViolationException or rethrow if needed
        return new ResponseEntity<>("An error occurred during the operation.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
