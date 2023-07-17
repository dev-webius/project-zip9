package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.AnnouncementEntity;
import com.zip9.api.announcement.entity.SupplyScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplyScheduleRepository extends JpaRepository<SupplyScheduleEntity, Long> {
    List<SupplyScheduleEntity> findAllByAnnouncement(AnnouncementEntity announcement);
}
