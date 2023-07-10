package com.zip9.api.LH.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
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

    @Builder.Default
    private LHAnnouncementDetailResponse detail = new LHAnnouncementDetailResponse();
    @Builder.Default
    private LHAnnouncementSupplyInfoResponse supplyInfo = new LHAnnouncementSupplyInfoResponse();

    public void setDetail(LHAnnouncementDetailResponse detail) {
        this.detail = detail;
    }

    public void setSupplyInfo(LHAnnouncementSupplyInfoResponse supplyInfo) {
        this.supplyInfo = supplyInfo;
    }
}
