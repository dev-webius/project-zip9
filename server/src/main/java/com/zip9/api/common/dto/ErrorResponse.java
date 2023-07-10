package com.zip9.api.common.dto;

import com.zip9.api.common.enums.Code;
import org.springframework.validation.BindingResult;

public class ErrorResponse extends BaseResponse {

    private ErrorResponse(Code code) {
        super(false, code.getCode(), code.getMessage());
    }

    private ErrorResponse(Code code, Exception e) {
        super(false, code.getCode(), code.getMessage());
    }

    private ErrorResponse(Code code, String message) {
        super(false, code.getCode(), code.getMessage(message));
    }

    public static ErrorResponse of(Code code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(Code code, Exception e) {
        return new ErrorResponse(code, e);
    }

    public static ErrorResponse of(Code code, String message) {
        return new ErrorResponse(code, message);
    }

    public static ErrorResponse of(BindingResult binding) {
        return new ErrorResponse(null);
    }
}
