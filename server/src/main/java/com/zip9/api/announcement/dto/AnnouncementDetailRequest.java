package com.zip9.api.announcement.dto;

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
}
