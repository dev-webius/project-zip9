package com.zip9.api.naver.service;

import com.zip9.api.naver.client.NaverApiClient;
import com.zip9.api.naver.dto.GeocodingRequest;
import com.zip9.api.naver.dto.GeocodingResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GeocodingService {
    private final NaverApiClient client;

    public GeocodingResponse.Address findAddress(String query) {
        GeocodingResponse response = client.geocode(GeocodingRequest.builder()
                .query(query)
                .build());

        return response.getAddresses().stream().findAny().orElse(new GeocodingResponse.Address());
    }
}
