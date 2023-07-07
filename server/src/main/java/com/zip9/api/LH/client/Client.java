package com.zip9.api.LH.client;

import com.zip9.api.LH.dto.LHAnnouncementDetailResponse;
import com.zip9.api.LH.dto.LHAnnouncementRequest;
import com.zip9.api.LH.dto.LHAnnouncementResponse;
import com.zip9.api.LH.dto.LHAnnouncementSupplyInfoResponse;
import com.zip9.api.announcement.dto.AnnouncementDetailRequest;
import com.zip9.api.announcement.dto.AnnouncementSupplyInfoRequest;

import java.util.List;

public interface Client {
    public List<LHAnnouncementResponse> getAnnouncements(LHAnnouncementRequest request);

    public LHAnnouncementSupplyInfoResponse getAnnouncementSupplyInfo(AnnouncementSupplyInfoRequest request);

    public LHAnnouncementDetailResponse getAnnouncementDetail(AnnouncementDetailRequest request);
}
