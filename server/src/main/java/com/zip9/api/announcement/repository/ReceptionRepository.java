package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.AnnouncementEntity;
import com.zip9.api.announcement.entity.ReceptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceptionRepository extends JpaRepository<ReceptionEntity, Long> {
    ReceptionEntity findByAnnouncement(AnnouncementEntity announcement);
}
