package com.zip9.api.announcement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@Builder
@NoArgsConstructor
public class LabelAndValue {
    private String label;
    private String value;

    public LabelAndValue(String label, String value) {
        if (StringUtils.hasLength(label) && StringUtils.hasLength(value)) {
            this.label = label;
            this.value = value;
        } else {
            this.label = "";
            this.value = "";
        }
    }

    public void setTermValue(String startDate, String endDate) {
        if (StringUtils.hasLength(this.label) && StringUtils.hasLength(startDate) && StringUtils.hasLength(endDate)) {
            this.value = startDate + " ~ " + endDate;
        } else {
            this.value = "";
        }
    }
}
