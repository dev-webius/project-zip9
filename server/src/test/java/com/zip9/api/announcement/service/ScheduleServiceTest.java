package com.zip9.api.announcement.service;

import com.zip9.api.LH.dto.LHAnnouncementRequest;
import com.zip9.api.LH.dto.LHAnnouncementResponse;
import com.zip9.api.LH.repository.RawDataRepository;
import com.zip9.api.LH.service.LHService;
import com.zip9.api.LH.service.ScheduleService;
import com.zip9.api.announcement.dto.AnnouncementRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    LHService lhService;
    @Autowired
    ScheduleService scheduler;

    @Autowired
    RawDataRepository rawDataRepository;

//    @Test
//    void 공고조회_초기_마이그레이션_테스트() {
//        scheduler.initialMigration();
//    }

    @Test
    void 공고조회_마이그레이션_스케쥴러_테스트() {
        scheduler.migration();
    }

    @Test
    void Date_to_Datetime_테스트() {
        List<LHAnnouncementRequest> requests = AnnouncementRequest.builder()
                .registStartDate(LocalDate.parse("2023-07-01"))
                .registEndDate(LocalDate.parse("2023-07-04"))
                .build().buildLHRequests();

        List<LHAnnouncementResponse> announcements = requests.stream()
                .map(request -> lhService.searchAnnouncements(request))
                .flatMap(List::stream)
                .toList()
                .stream()
                .sorted(Comparator.comparing(LHAnnouncementResponse::getRegistDate).reversed())
                .toList();

//        List<LocalDateTime> localDateTimes = announcements.stream().map(announcement -> LocalDateTime.of(announcement.getRegistDate(), LocalTime.MIN)).toList();
//        List<LocalDateTime> localDateTimes1 = announcements.stream().map(announcement -> LocalDateTime.of(announcement.getCloseDate(), LocalTime.MIN)).toList();
//        List<LocalDateTime> localDateTimes2 = announcements.stream().map(announcement -> LocalDateTime.of(announcement.getAnnouncementDate(), LocalTime.MIN)).toList();

        assert true;
    }

}