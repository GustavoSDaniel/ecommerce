package com.gustavosdaniel.backend.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse (
        String error,
        String message,
        LocalDateTime timestamp,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Map<String, String> fieldsErrors
){
}
