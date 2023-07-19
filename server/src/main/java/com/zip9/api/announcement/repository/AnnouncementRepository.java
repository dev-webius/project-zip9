package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long>, AnnouncementCustomRepository {
    List<AnnouncementEntity> findAllByStatusCodeIn(List<String> statusCodes);
    List<AnnouncementEntity> findAllByStatusCodeNotAndClosedAtBefore(String statusCode, LocalDateTime closedAt);
}
