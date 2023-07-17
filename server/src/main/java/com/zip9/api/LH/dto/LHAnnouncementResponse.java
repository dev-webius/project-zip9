package com.zip9.api.LH.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zip9.api.common.exception.GeneralException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LHAnnouncementResponse {
    @JsonProperty("PAN_ID")
    private String id;

    @JsonProperty("PAN_NM")
    private String title;

    @JsonProperty("PAN_SS")
    private String announcementStatusName;

    @JsonProperty("RNUM")
    private Long rowNum;

    @JsonProperty("UPP_AIS_TP_CD")
    private String announcementTypeCode;

    @JsonProperty("UPP_AIS_TP_NM")
    private String announcementTypeName;

    @JsonProperty("AIS_TP_CD")
    private String announcementDetailTypeCode;

    @JsonProperty("AIS_TP_CD_NM")
    private String announcementDetailTypeName;

    @JsonProperty("SPL_INF_TP_CD")
    private String supplyTypeCode;

    @JsonProperty("CNP_CD_NM")
    private String cityName;

    @JsonProperty("CCR_CNNT_SYS_DS_CD")
    private String crmCode;

    @JsonProperty("DTL_URL_MOB")
    private String detailUrlMobile;

    @JsonProperty("DTL_URL")
    private String detailUrl;

    @JsonProperty("PAN_DT")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate announcementDate;

    @JsonProperty("CLSG_DT")
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate closeDate;

    @JsonProperty("PAN_NT_ST_DT")
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate registDate;

    @JsonProperty("ALL_CNT")
    private Long view;

    public static LHAnnouncementResponse jsonToObject(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(json, LHAnnouncementResponse.class);
        } catch (JsonProcessingException e) {
            throw new GeneralException(e);
        }
    }
}
