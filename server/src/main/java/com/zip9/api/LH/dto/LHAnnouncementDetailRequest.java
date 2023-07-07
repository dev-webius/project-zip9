package com.zip9.api.LH.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LHAnnouncementDetailRequest {
    @JsonProperty("PAN_ID")
    private String announcementId;
    @JsonProperty("AIS_TP_CD")
    private String announcementTypeCode;
    @JsonProperty("UPP_AIS_TP_CD")
    private String announcementDetailTypeCode;
    @JsonProperty("SPL_INF_TP_CD")
    private String supplyTypeCode;
    @JsonProperty("CCR_CNNT_SYS_DS_CD")
    private String crmCode;
}
