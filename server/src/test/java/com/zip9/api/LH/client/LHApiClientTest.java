package com.zip9.api.LH.client;

import com.zip9.api.announcement.dto.AnnouncementRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class LHApiClientTest {

    @Autowired
    LHApiClient apiClient;

    @Test
    void 공고명_검색_1() {
        // given
//        AnnouncementRequest dto = AnnouncementRequest.builder()
//                .title("서울")
//                .page(1)
//                .size(10)
//                .build();

        // when
        // then
//        assertThatNoException().isThrownBy(() -> apiClient.getAnnouncements(dto));
    }



}