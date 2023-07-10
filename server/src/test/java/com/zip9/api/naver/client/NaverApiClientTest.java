package com.zip9.api.naver.client;

import com.zip9.api.naver.dto.GeocodingRequest;
import com.zip9.api.naver.dto.GeocodingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NaverApiClientTest {

    @Autowired
    NaverApiClient client;

    @Test
    void geocoding_api_연동_테스트() {
        // given
        GeocodingRequest request = GeocodingRequest.builder()
                .query("광교중앙로 145")
                .build();

        // when
        GeocodingResponse geocode = client.geocode(request);
        // then
    }


}