package com.buildmyself.job_application_tracker.exception;

import com.buildmyself.job_application_tracker.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse> handleBusinessException(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(CommonResponse.error(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.getHttpStatus())
                .body(CommonResponse.error(
                        ErrorCode.VALIDATION_ERROR,
                        ErrorCode.VALIDATION_ERROR.getDefaultMessage(),
                        errors));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CommonResponse> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();

        for (ParameterValidationResult result : ex.getParameterValidationResults()) {
            if (result instanceof ParameterErrors pe) {
                // errors inside a @Valid @RequestBody object
                pe.getFieldErrors().forEach(fe ->
                        errors.putIfAbsent(fe.getField(),
                                Objects.requireNonNullElse(fe.getDefaultMessage(), "invalid")));
            } else {
                // errors on @RequestParam / @PathVariable
                String name = result.getMethodParameter().getParameterName();
                result.getResolvableErrors().forEach(err ->
                        errors.putIfAbsent(name,
                                Objects.requireNonNullElse(err.getDefaultMessage(), "invalid")));
            }
        }

        ErrorCode code = ErrorCode.VALIDATION_ERROR;
        return ResponseEntity.status(code.getHttpStatus())
                .body(CommonResponse.error(code, code.getDefaultMessage(), errors));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CommonResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        return ResponseEntity
                .status(ErrorCode.FAIL.getHttpStatus())
                .body(CommonResponse.error(
                        errorCode,
                        errorCode.getDefaultMessage(),
                        Map.of(ex.getParameterName(), ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleException(Exception ex) {
        log.error("Error: {}", ex.getMessage());
        return ResponseEntity
                .status(ErrorCode.FAIL.getHttpStatus())
                .body(CommonResponse.error(ErrorCode.FAIL));
    }
}
