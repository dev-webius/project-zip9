package com.zip9.api.announcement.service;

import com.zip9.api.announcement.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SpringBootTest
class AnnouncementServiceTest {
    @Autowired
    AnnouncementService announcementService;

    @Test
    void 공고_상세조회_테스트() {
        // given
        AnnouncementResponse response = announcementService.searchAnnouncements(AnnouncementRequest.builder()
                .registStartDate(LocalDate.parse("2023-07-01"))
                .registEndDate(LocalDate.parse("2023-07-11"))
                .build());

        LinkedHashMap<String, List<Announcement>> announcementMap = response.getItem().getAnnouncements();


        // when
        List<AnnouncementDetailsResponse> responses = new ArrayList<>();
        for (String key : announcementMap.keySet()) {
            List<Announcement> announcements = announcementMap.get(key);

            for (Announcement announcement : announcements) {
                AnnouncementDetailRequest request = AnnouncementDetailRequest.builder()
                        .announcementId(announcement.getId())
                        .supplyType(announcement.getSupplyType())
                        .announcementType(announcement.getAnnouncementType())
                        .announcementDetailType(announcement.getAnnouncementDetailType())
                        .csTypeCode(announcement.getCsTypeCode())
                        .build();

                responses.add(announcementService.getAnnouncementDetail(request));
            }
        }
    }

}