package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.AnnouncementEntity;
import com.zip9.api.announcement.entity.EtcEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtcRepository extends JpaRepository<EtcEntity, Long> {
    EtcEntity findByAnnouncement(AnnouncementEntity announcement);
}
