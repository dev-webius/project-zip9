package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.dto.Announcement;
import com.zip9.api.announcement.dto.AnnouncementRequest;

import java.util.List;

public interface AnnouncementCustomRepository {
    List<Announcement> findAnnouncements(AnnouncementRequest request);
    Long countAnnouncements(AnnouncementRequest request);
}
