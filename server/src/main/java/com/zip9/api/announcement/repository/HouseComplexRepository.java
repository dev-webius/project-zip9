package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.AnnouncementEntity;
import com.zip9.api.announcement.entity.HouseComplexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseComplexRepository extends JpaRepository<HouseComplexEntity, Long> {
    List<HouseComplexEntity> findAllByAnnouncement(AnnouncementEntity announcement);
    List<HouseComplexEntity> findAllByAnnouncementId(Long announcementId);

    @Query("select hc from HouseComplexEntity hc join fetch hc.houseComplexPositionEntity where hc.announcement in :announcements")
    List<HouseComplexEntity> findAllByAnnouncements(@Param("announcements") List<AnnouncementEntity> announcements);
}
