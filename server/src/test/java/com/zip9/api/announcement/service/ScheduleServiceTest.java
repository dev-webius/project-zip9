package com.zip9.api.announcement.service;

import com.zip9.api.LH.repository.RawDataRepository;
import com.zip9.api.LH.service.LHService;
import com.zip9.api.LH.service.ScheduleService;
import com.zip9.api.announcement.dto.AnnouncementsMigrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    LHService lhService;
    @Autowired
    ScheduleService scheduler;

    @Autowired
    RawDataRepository rawDataRepository;

    @Test
    void 공고조회_마이그레이션_스케쥴러_테스트() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate today = LocalDate.now();

        scheduler.migration(AnnouncementsMigrationRequest.builder()
                .from(LocalDate.parse("2023-07-06"))
                .to(LocalDate.parse("2023-07-08"))
                .build());
    }

}