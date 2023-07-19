package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.AnnouncementEntity;
import com.zip9.api.announcement.entity.QualificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualificationRepository extends JpaRepository<QualificationEntity, Long> {
    List<QualificationEntity> findAllByAnnouncement(AnnouncementEntity announcement);
}
