package com.zip9.api.announcement.service;

import com.zip9.api.announcement.dto.AnnouncementRequest;
import com.zip9.api.announcement.dto.AnnouncementsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;

@SpringBootTest
class AnnouncementOpenAPIServiceTest {
    @Autowired
    AnnouncementOpenAPIService announcementOpenAPIService;

    @Test
    void 공고_리스트조회_테스트() {
        // given
        AnnouncementsResponse response = announcementOpenAPIService.getAnnouncements(AnnouncementRequest.builder()
                .registStartDate(LocalDate.parse("2023-07-01"))
                .registEndDate(LocalDate.parse("2023-07-04"))
                .build());

        Assert.isTrue(Boolean.TRUE, "");
    }

//    @Test
//    void 공고_상세조회_테스트() {
//        // given
//        AnnouncementResponse response = announcementOpenAPIService.getAnnouncements(AnnouncementRequest.builder()
//                .registStartDate(LocalDate.parse("2023-07-01"))
//                .registEndDate(LocalDate.parse("2023-07-04"))
//                .build());
//
//        LinkedHashMap<String, List<Announcement>> announcementMap = response.getItem().getAnnouncements();
//
//        // when
//        List<AnnouncementDetailResponse> responses = new ArrayList<>();
//        for (String key : announcementMap.keySet()) {
//            List<Announcement> announcements = announcementMap.get(key);
//
//            for (Announcement announcement : announcements) {
//                AnnouncementDetailRequest request = AnnouncementDetailRequest.builder()
//                        .announcementId(announcement.getId())
//                        .supplyType(announcement.getSupplyType())
//                        .announcementType(announcement.getAnnouncementType())
//                        .announcementDetailType(announcement.getAnnouncementDetailType())
//                        .csTypeCode(announcement.getCsTypeCode())
//                        .build();
//
//                responses.add(announcementOpenAPIService.getAnnouncementDetail(request));
//            }
//        }
//    }

}