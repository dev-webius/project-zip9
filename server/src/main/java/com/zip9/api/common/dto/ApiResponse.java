package com.zip9.api.common.dto;

import com.zip9.api.common.enums.Code;
import lombok.Getter;

@Getter
public class ApiResponse<T> extends BaseResponse {
    private final T data;

    public ApiResponse(T data) {
        super(true, Code.OK.getCode(), Code.OK.getMessage());
        this.data = data;
    }

    private ApiResponse(T data, String message) {
        super(true, Code.OK.getCode(), message);
        this.data = data;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> of(T data, String message) {
        return new ApiResponse<>(data, message);
    }

    public static <T> ApiResponse<T> empty() {
        return new ApiResponse<>(null);
    }

}
