package com.zip9.api.announcement.service;

import com.zip9.api.announcement.dto.*;
import com.zip9.api.announcement.repository.AnnouncementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AnnouncementDBServiceTest {
    @Autowired
    AnnouncementDBService announcementDBService;

    @Autowired
    AnnouncementRepository announcementRepository;

    @Test
    void getAnnouncements_테스트() {
        AnnouncementResponse announcements = announcementDBService.getAnnouncements(AnnouncementRequest.builder()
                .registStartDate(LocalDate.parse("2023-07-04"))
                .registEndDate(LocalDate.parse("2023-07-10"))
//                .closeStartDate(LocalDate.parse("2023-07-01"))
//                .closeEndDate(LocalDate.parse("2023-07-30"))
//                .city(City.GYEONGGI.name())
//                .title("화성")
                .build()
        );

        assertTrue(true);
    }

    @Test
    void getAnnouncementDetail_테스트() {
        List<Announcement> announcements = announcementRepository.findAnnouncements(AnnouncementRequest.builder()
                .registStartDate(LocalDate.parse("2023-07-04"))
                .registEndDate(LocalDate.parse("2023-07-10"))
                .build()
        );

        List<AnnouncementDetailResponse> announcementDetails = announcements.stream()
                .map(announcement -> announcementDBService.getAnnouncementDetail(AnnouncementDetailRequest.builder()
                        .announcementId(announcement.getId())
                        .build())
                )
                .toList();

        assertTrue(true);
    }
}