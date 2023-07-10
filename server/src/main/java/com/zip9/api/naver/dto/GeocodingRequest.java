package com.zip9.api.naver.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeocodingRequest {
    @NotNull
    private String query;
    private String coordinate;
    private String filter;
    @Min(1)
    private Integer page;
    @Range(min = 1, max = 100)
    private Integer count;
}
