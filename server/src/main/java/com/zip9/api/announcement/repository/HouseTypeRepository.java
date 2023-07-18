package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.HouseComplexEntity;
import com.zip9.api.announcement.entity.HouseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseTypeRepository extends JpaRepository<HouseTypeEntity, Long> {
    List<HouseTypeEntity> findAllByHouseComplex(HouseComplexEntity houseComplex);
}
