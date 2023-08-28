package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.dto.AnnouncementRequest;
import com.zip9.api.announcement.entity.AnnouncementEntity;

import java.util.List;

public interface AnnouncementCustomRepository {
    List<AnnouncementEntity> findAnnouncements(AnnouncementRequest request);
    Long countAnnouncements(AnnouncementRequest request);
}
