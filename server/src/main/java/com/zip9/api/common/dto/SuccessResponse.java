package com.zip9.api.common.dto;

import com.zip9.api.common.enums.Code;
import lombok.Getter;

@Getter
public class SuccessResponse<T, M> extends BaseResponse {
    private final M meta;
    private final T data;

    public SuccessResponse(T data) {
        super(true, Code.OK.getCode(), Code.OK.getMessage());
        this.data = data;
        this.meta = null;
    }

    public SuccessResponse(T data, M meta) {
        super(true, Code.OK.getCode(), Code.OK.getMessage());
        this.data = data;
        this.meta = meta;
    }

    private SuccessResponse(T data, M meta, String message) {
        super(true, Code.OK.getCode(), message);
        this.data = data;
        this.meta = meta;
    }

    public static <T, M> SuccessResponse<T, M> of(T data) {
        return new SuccessResponse<>(data);
    }

    public static <T, M> SuccessResponse<T, M> of(T data, M meta) {
        return new SuccessResponse<>(data, meta);
    }

    public static <T, M> SuccessResponse<T, M> of(T data, M meta, String message) {
        return new SuccessResponse<>(data, meta, message);
    }

    public static <T, M> SuccessResponse<T, M> empty() {
        return new SuccessResponse<>(null);
    }

}
