package com.zip9.api.common.enums;

import com.zip9.api.common.exception.GeneralException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum Code {
    OK(200, HttpStatus.OK, "OK"),

    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(401, HttpStatus.BAD_REQUEST, "Validation error"),
    UNAUTHORIZED(403, HttpStatus.UNAUTHORIZED, "User unauthorized"),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "Requested resource is not found"),

    INTERNAL_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR(501, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error");


    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public static Code valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) {
                        return Code.BAD_REQUEST;
                    } else if (httpStatus.is5xxServerError()) {
                        return Code.INTERNAL_ERROR;
                    } else {
                        return Code.OK;
                    }
                });
    }

    public static Code valueOf(HttpStatusCode httpStatusCode) {
        return valueOf(HttpStatus.valueOf(httpStatusCode.value()));
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}
