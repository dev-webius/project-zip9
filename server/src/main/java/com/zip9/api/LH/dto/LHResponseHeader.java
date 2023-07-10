package com.zip9.api.LH.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LHResponseHeader {
    @JsonProperty("SS_CODE")
    private String code;

    @JsonProperty("RS_DTTM")
    private String respondAt;
}
