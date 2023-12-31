package com.zip9.api.announcement.service;

import com.zip9.api.announcement.dto.AnnouncementRequest;
import com.zip9.api.announcement.dto.AnnouncementsResponse;
import com.zip9.api.announcement.repository.AnnouncementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AnnouncementDBServiceTest {
    @Autowired
    AnnouncementDBService announcementDBService;
    @Autowired
    AnnouncementRepository announcementRepository;

    @Test
    void getAnnouncements_테스트() {
        LocalDate today = LocalDate.now();

        AnnouncementsResponse announcements = announcementDBService.getAnnouncements(AnnouncementRequest.builder()
                .build()
        );

        assertTrue(true);
    }

    @Test
    void getAnnouncementDetail_테스트() {
//        List<Announcement> announcements = announcementRepository.findAnnouncements(AnnouncementRequest.builder()
//                .build()
//        );
//
//        List<AnnouncementDetailResponse> announcementDetails = announcements.stream()
//                .map(announcement -> announcementDBService.getAnnouncementDetail(AnnouncementDetailRequest.builder()
//                        .announcementId(announcement.getId())
//                        .build())
//                )
//                .toList();

        assertTrue(true);
    }

//    @Test
//    void open_api_vs_db_성능_테스트1() {
//        // given
//        AnnouncementRequest request = AnnouncementRequest.builder()
//                .registStartDate(LocalDate.parse("2023-07-01"))
//                .registEndDate(LocalDate.parse("2023-07-04"))
//                .build();
//
//        // when-1
//        StopWatch stopWatch1 = new StopWatch("OPEN_API_STOP_WATCH");
//        stopWatch1.start("[START] - getAnnouncements");
//        AnnouncementsResponse r1 = announcementOpenAPIService.getAnnouncements(request);
//        stopWatch1.stop();
//
//        // when-2
//        StopWatch stopWatch2 = new StopWatch("DB_STOP_WATCH");
//        stopWatch2.start("[START] - getAnnouncements");
//        AnnouncementsResponse r2 = announcementDBService.getAnnouncements(request);
//        stopWatch2.stop();
//
//        // then
//        printResult(stopWatch1);
//        printResult(stopWatch2);
//    }
//
//    private void printResult(StopWatch stopWatch1) {
//        System.out.println(stopWatch1.prettyPrint());
//        System.out.println("마지막 작업 걸린 시간 : " + stopWatch1.getLastTaskTimeNanos());
//        System.out.println("totalTimeSeconds : " + stopWatch1.getTotalTimeSeconds());
//    }
}