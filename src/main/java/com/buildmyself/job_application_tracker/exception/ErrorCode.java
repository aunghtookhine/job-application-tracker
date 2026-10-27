package com.buildmyself.job_application_tracker.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SUCCESS("0000", "Success", HttpStatus.OK),
    FAIL("1000", "Something went wrong", HttpStatus.BAD_REQUEST),
    INVALID_APPLICATION_ID("1001", "Invalid Application ID", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR("1002", "Validation Error", HttpStatus.BAD_REQUEST),

    // Auth Error - 3xxx
    EMAIL_ALREADY_REGISTERED("3001", "Email Already Registered", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS("3002", "Invalid Email or Password", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("3003", "Authentication Required", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("3004", "Access Denied", HttpStatus.FORBIDDEN),

    INTERNAL_ERROR("9999", "Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;


}
