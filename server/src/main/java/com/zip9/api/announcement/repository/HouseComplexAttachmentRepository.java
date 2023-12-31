package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.HouseComplexAttachmentEntity;
import com.zip9.api.announcement.entity.HouseComplexEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseComplexAttachmentRepository extends JpaRepository<HouseComplexAttachmentEntity, Long> {
    List<HouseComplexAttachmentEntity> findAllByHouseComplex(HouseComplexEntity houseComplex);
}
