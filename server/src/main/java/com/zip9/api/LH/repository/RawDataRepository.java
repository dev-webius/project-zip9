package com.zip9.api.LH.repository;

import com.zip9.api.LH.entity.RawDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RawDataRepository extends JpaRepository<RawDataEntity, Long> {
    List<RawDataEntity> findAllByStatusEqualsIgnoreCaseAndCreatedAtAfter(String status, LocalDateTime createdAt);
}
