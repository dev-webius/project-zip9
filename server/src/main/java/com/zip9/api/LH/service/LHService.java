package com.zip9.api.LH.service;

import com.zip9.api.LH.client.Client;
import com.zip9.api.LH.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LHService {
    private final Client client;

    public List<LHAnnouncementResponse> searchAnnouncements(LHAnnouncementRequest request) {
        return client.getAnnouncements(request);
    }

    public LHAnnouncementDetailResponse getAnnouncementDetail(LHAnnouncementDetailAndSupplyRequest request) {
        return client.getAnnouncementDetail(request);
    }

    public LHAnnouncementSupplyInfoResponse getAnnouncementSupplyInfo(LHAnnouncementDetailAndSupplyRequest request) {
        return client.getAnnouncementSupplyInfo(request);
    }

}
