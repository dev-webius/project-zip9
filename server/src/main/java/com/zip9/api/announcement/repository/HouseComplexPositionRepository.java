package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.HouseComplexEntity;
import com.zip9.api.announcement.entity.HouseComplexPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseComplexPositionRepository extends JpaRepository<HouseComplexPositionEntity, Long> {
    HouseComplexPositionEntity findByHouseComplex(HouseComplexEntity houseComplex);
}
