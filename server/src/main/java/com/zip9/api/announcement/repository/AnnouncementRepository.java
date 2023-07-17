package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {
    List<AnnouncementEntity> findAllByStatusCodeIn(List<String> statusCodes);
}
