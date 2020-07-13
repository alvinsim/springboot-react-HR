package com.example.hr.controller.responsehandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String error = e.getParameterName() + " parameter is missing";
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.RESPONSE_STATUS_ERROR)
                .message(error)
                .build();
        return buildResponseEntity(apiResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(e.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        e.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.RESPONSE_STATUS_ERROR)
                .message(builder.toString())
                .build();
        return buildResponseEntity(apiResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.RESPONSE_STATUS_ERROR)
                .message("Validation error")
                .build();
        return buildResponseEntity(apiResponse, BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.RESPONSE_STATUS_ERROR)
                .message(e.getMessage())
                .build();
        return buildResponseEntity(apiResponse, NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.RESPONSE_STATUS_ERROR)
                .message(error)
                .build();
        return buildResponseEntity(apiResponse, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String error = "Error writing JSON output";
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.RESPONSE_STATUS_ERROR)
                .message(error)
                .build();
        return buildResponseEntity(apiResponse, INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.RESPONSE_STATUS_ERROR)
                .message(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()))
                .build();
        return buildResponseEntity(apiResponse, BAD_REQUEST);
    }

    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.RESPONSE_STATUS_ERROR)
                .message(ex.getMessage())
                .build();
        return buildResponseEntity(apiResponse, NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException e,
            WebRequest request) {
        if (e.getCause() instanceof ConstraintViolationException) {
            ApiResponse apiResponseConflict = ApiResponse
                    .builder()
                    .status(ApiResponse.RESPONSE_STATUS_ERROR)
                    .message("Database error")
                    .build();
            return buildResponseEntity(apiResponseConflict, CONFLICT);
        }
        ApiResponse apiResponseError = ApiResponse
                .builder()
                .status(ApiResponse.RESPONSE_STATUS_ERROR)
                .message(e.getMessage())
                .build();
        return buildResponseEntity(apiResponseError, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e,
            WebRequest request) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(ApiResponse.RESPONSE_STATUS_ERROR)
                .message(String.format(
                        "The parameter '%s' of value '%s' could not be converted to type '%s'",
                        e.getName(),
                        e.getValue(),
                        e.getRequiredType().getSimpleName()))
                .build();
        return buildResponseEntity(apiResponse, BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiResponse apiResponse, HttpStatus status) {
        return new ResponseEntity<>(apiResponse, status);
    }
}