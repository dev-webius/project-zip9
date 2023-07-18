package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.AnnouncementEntity;
import com.zip9.api.announcement.entity.HouseComplexEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseComplexRepository extends JpaRepository<HouseComplexEntity, Long> {
    List<HouseComplexEntity> findAllByAnnouncement(AnnouncementEntity announcement);
    List<HouseComplexEntity> findAllByAnnouncementId(Long announcementId);
}
