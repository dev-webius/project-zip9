package com.zip9.api.announcement.dto;

import com.zip9.api.LH.dto.LHAnnouncementResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDetailRequest {
    private String announcementId;
    private String announcementTypeCode;
    private String announcementDetailTypeCode;
    private String supplyTypeCode;
    private String crmCode;

    @Builder(builderClassName = "ByLHAnnouncement", builderMethodName = "ByLHAnnouncement")
    public AnnouncementDetailRequest(LHAnnouncementResponse announcement) {
        this.announcementId = announcement.getId();
        this.announcementTypeCode = announcement.getAnnouncementTypeCode();
        this.announcementDetailTypeCode = announcement.getAnnouncementDetailTypeCode();
        this.crmCode = announcement.getCrmCode();
        this.supplyTypeCode = announcement.getSupplyTypeCode();
    }
}
