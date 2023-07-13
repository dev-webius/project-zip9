package com.zip9.api.LH.client;

import com.zip9.api.LH.dto.*;

import java.util.List;

public interface Client {
    public List<LHAnnouncementResponse> getAnnouncements(LHAnnouncementRequest request);

    public LHAnnouncementSupplyInfoResponse getAnnouncementSupplyInfo(LHAnnouncementDetailAndSupplyRequest request);

    public LHAnnouncementDetailResponse getAnnouncementDetail(LHAnnouncementDetailAndSupplyRequest request);
}
