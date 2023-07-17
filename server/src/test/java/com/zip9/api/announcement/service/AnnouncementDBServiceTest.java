package com.zip9.api.announcement.service;

import com.zip9.api.LH.enums.City;
import com.zip9.api.announcement.dto.AnnouncementDetailRequest;
import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.announcement.dto.AnnouncementRequest;
import com.zip9.api.announcement.dto.AnnouncementResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AnnouncementDBServiceTest {
    @Autowired
    AnnouncementDBService announcementDBService;

    @Test
    void getAnnouncements_테스트() {
        AnnouncementResponse announcements = announcementDBService.getAnnouncements(AnnouncementRequest.builder()
                .registStartDate(LocalDate.parse("2023-07-01"))
                .registEndDate(LocalDate.parse("2023-07-04"))
                .closeStartDate(LocalDate.parse("2023-07-01"))
                .closeEndDate(LocalDate.parse("2023-07-30"))
                .city(City.GYEONGGI.name())
                .title("화성")
                .build());

        assertTrue(true);
    }

    @Test
    void getAnnouncementDetail_테스트() {
        Long announcementId = 277L;

        AnnouncementDetailResponse announcementDetail = announcementDBService.getAnnouncementDetail(AnnouncementDetailRequest.builder()
                .announcementId(announcementId)
                .build());

        assertTrue(true);
    }
}