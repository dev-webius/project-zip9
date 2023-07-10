package com.zip9.api.common.dto;

import com.zip9.api.common.enums.Code;
import lombok.Getter;

@Getter
public class SuccessResponse<T> extends BaseResponse {
    private final T data;

    public SuccessResponse(T data) {
        super(true, Code.OK.getCode(), Code.OK.getMessage());
        this.data = data;
    }

    private SuccessResponse(T data, String message) {
        super(true, Code.OK.getCode(), message);
        this.data = data;
    }

    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>(data);
    }

    public static <T> SuccessResponse<T> of(T data, String message) {
        return new SuccessResponse<>(data, message);
    }

    public static <T> SuccessResponse<T> empty() {
        return new SuccessResponse<>(null);
    }

}
