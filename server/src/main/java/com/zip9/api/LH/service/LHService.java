package com.zip9.api.LH.service;

import com.zip9.api.LH.client.LHApiClient;
import com.zip9.api.LH.dto.LHAnnouncementDetailResponse;
import com.zip9.api.LH.dto.LHAnnouncementRequest;
import com.zip9.api.LH.dto.LHAnnouncementResponse;
import com.zip9.api.LH.dto.LHAnnouncementSupplyInfoResponse;
import com.zip9.api.announcement.dto.AnnouncementDetailRequest;
import com.zip9.api.announcement.dto.AnnouncementSupplyInfoRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LHService {
    private final LHApiClient client;

    public List<LHAnnouncementResponse> searchAnnouncements(LHAnnouncementRequest request) {
        return client.getAnnouncements(request);
    }

    public LHAnnouncementDetailResponse getAnnouncementDetail(AnnouncementDetailRequest request) {
        return client.getAnnouncementDetail(request);
    }

    public LHAnnouncementSupplyInfoResponse getAnnouncementSupplyInfo(AnnouncementSupplyInfoRequest request) {
        return client.getAnnouncementSupplyInfo(request);
    }

}
