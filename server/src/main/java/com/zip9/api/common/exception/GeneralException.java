package com.zip9.api.common.exception;

import com.zip9.api.common.enums.Code;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private final Code code;

    public GeneralException() {
        super(Code.INTERNAL_ERROR.getMessage());
        this.code = Code.INTERNAL_ERROR;
    }

    public GeneralException(String message) {
        super(Code.INTERNAL_ERROR.getMessage(message));
        this.code = Code.INTERNAL_ERROR;
    }

    public GeneralException(String message, Throwable cause) {
        super(Code.INTERNAL_ERROR.getMessage(message), cause);
        this.code = Code.INTERNAL_ERROR;
    }

    public GeneralException(Throwable cause) {
        super(Code.INTERNAL_ERROR.getMessage(cause));
        this.code = Code.INTERNAL_ERROR;
    }

    public GeneralException(Code code) {
        super(code.getMessage());
        this.code = code;
    }

    public GeneralException(Code code, String message) {
        super(code.getMessage(message));
        this.code = code;
    }

    public GeneralException(Code code, String message, Throwable cause) {
        super(code.getMessage(message), cause);
        this.code = code;
    }

    public GeneralException(Code code, Throwable cause) {
        super(code.getMessage(cause), cause);
        this.code = code;
    }
}
