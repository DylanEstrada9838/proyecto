package org.bedu.proyecto.config;

import java.util.LinkedList;
import java.util.List;

import org.bedu.proyecto.dto.ErrorDTO;
import org.bedu.proyecto.exception.RuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO validationError(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = new LinkedList<>();

        for (FieldError fieldError : fieldErrors) {
            String errorMessage = String.format("Field '%s' %s", fieldError.getField(), fieldError.getDefaultMessage());
            errors.add(errorMessage);
        }

        return ErrorDTO.builder()
                .code("ERR_VALID")
                .message("Los datos de entrada contiene errores")
                .details(errors)
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO runtimeError(RuntimeException ex) {
        return ErrorDTO.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .details(ex.getDetails())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO unknownError(Exception ex) {
        log.error(ex.getMessage());
        return ErrorDTO.builder()
                .code("ERR_UNKNOWN")
                .message("Unknown Error")
                .build();
    }
}
