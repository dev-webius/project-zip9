package com.zip9.api.LH.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zip9.api.LH.enums.AnnouncementDetailType;
import com.zip9.api.LH.enums.AnnouncementType;
import com.zip9.api.LH.enums.HouseSupplyType;
import com.zip9.api.announcement.dto.AnnouncementDetailRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LHAnnouncementDetailAndSupplyRequest {
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

    @Builder(builderMethodName = "ByAnnouncementDetailRequestBuilder", builderClassName = "ByAnnouncementDetailRequestBuilder")
    public LHAnnouncementDetailAndSupplyRequest(AnnouncementDetailRequest request) {
        this.announcementId = request.getAnnouncementId();
        this.announcementTypeCode = AnnouncementType.valueOf(request.getAnnouncementType()).code;
        this.announcementDetailTypeCode = AnnouncementDetailType.valueOf(request.getAnnouncementDetailType()).code;
        this.supplyTypeCode = HouseSupplyType.valueOf(request.getSupplyType()).code;
        this.crmCode = request.getCsTypeCode();
    }

    @Builder(builderMethodName = "ByLHAnnouncementBuilder", builderClassName = "builderMethodName")
    public LHAnnouncementDetailAndSupplyRequest(LHAnnouncementResponse lhAnnouncement) {
        this.announcementId = lhAnnouncement.getId();
        this.announcementTypeCode = lhAnnouncement.getAnnouncementTypeCode();
        this.announcementDetailTypeCode = lhAnnouncement.getAnnouncementDetailTypeCode();
        this.supplyTypeCode = lhAnnouncement.getSupplyTypeCode();
        this.crmCode = lhAnnouncement.getCrmCode();
    }
}
