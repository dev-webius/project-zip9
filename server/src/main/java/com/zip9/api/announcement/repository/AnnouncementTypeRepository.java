package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.AnnouncementTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementTypeRepository extends JpaRepository<AnnouncementTypeEntity, Long> {
}
