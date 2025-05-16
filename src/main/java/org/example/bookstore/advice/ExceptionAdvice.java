package org.example.bookstore.advice;

import org.example.bookstore.exception.CustomBadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<?> handleCustomBadRequest(CustomBadRequestException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(errorBody);
    }
}
