package com.zip9.api.common.dto;

import com.zip9.api.common.enums.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BaseResponse {
    private final Boolean status;
    private final Integer code;
    private final String message;

    public static BaseResponse of(Boolean status, Code code) {
        return new BaseResponse(status, code.getCode(), code.getMessage());
    }

    public static BaseResponse of(Boolean status, Code code, Exception e) {
        return new BaseResponse(status, code.getCode(), code.getMessage(e));
    }

    public static BaseResponse of(Boolean status, Code code, String message) {
        return new BaseResponse(status, code.getCode(), code.getMessage());
    }
}
