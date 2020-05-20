package com.mateo.exercise.http.handlers;

import com.mateo.exercise.http.models.responses.HttpErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ArrayList<String> errors = new ArrayList<>();

        for(FieldError fieldError: ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ": " +fieldError.getDefaultMessage());
        }

        for(ObjectError objError: ex.getBindingResult().getGlobalErrors()) {
            errors.add(objError.getDefaultMessage());
        }

        return handleExceptionInternal(
                ex,
                new HttpErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Data in Request Body", errors),
                headers,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<HttpErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        HttpErrorResponse errorResponse = new HttpErrorResponse(HttpStatus.BAD_REQUEST, "Wrong Argument Provided", ex.getLocalizedMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
