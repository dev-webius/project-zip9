package com.zip9.api.announcement.service;

import com.zip9.api.announcement.dto.AnnouncementDetailRequest;
import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.announcement.dto.AnnouncementRequest;
import com.zip9.api.announcement.dto.AnnouncementResponse;

public interface AnnouncementReadService {
    /**
     * 공고 목록 조회
     */
    public AnnouncementResponse getAnnouncements(AnnouncementRequest request);

    /**
     * 공고 상세정보 조회
     */
    public AnnouncementDetailResponse getAnnouncementDetail(AnnouncementDetailRequest request);
}
