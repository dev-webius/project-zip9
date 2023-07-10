package com.zip9.api.naver.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zip9.api.LH.dto.LHAnnouncementDetailResponse;
import com.zip9.api.LH.dto.LHAnnouncementResponse;
import com.zip9.api.LH.dto.LHAnnouncementSupplyInfoResponse;
import com.zip9.api.LH.enums.AnnouncementDetailType;
import com.zip9.api.LH.enums.AnnouncementStatus;
import com.zip9.api.LH.enums.City;
import com.zip9.api.announcement.dto.AnnouncementDetailRequest;
import com.zip9.api.announcement.dto.AnnouncementRequest;
import com.zip9.api.announcement.dto.AnnouncementSupplyInfoRequest;
import com.zip9.api.naver.dto.GeocodingRequest;
import com.zip9.api.naver.dto.GeocodingResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NaverApiClient {
    private final WebClient webClient;
    private static final String CLIENT_ID = "u1ba6jplzc";
    private static final String CLIENT_SECERT_KEY = "KFHmQEwqSo1wqlxIwdL683AvTRkCxQ6MaHZQKvE5";
    private static final String BASE_URL = "https://naveropenapi.apigw.ntruss.com";
    private static final String MAP_GEOCODING_URI = "/map-geocode/v2/geocode";

    public NaverApiClient() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", CLIENT_ID)
                .defaultHeader("X-NCP-APIGW-API-KEY", CLIENT_SECERT_KEY)
                .baseUrl(BASE_URL)
                .build();
    }

    public GeocodingResponse geocode(GeocodingRequest request) {
        return webClient
                .get()
                .uri(buildUriWithQueryParmas(request))
                .retrieve()
                .bodyToMono(GeocodingResponse.class)
                .block();
    }

    private String buildUriWithQueryParmas(GeocodingRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath(MAP_GEOCODING_URI)
                .queryParam("query", request.getQuery());

        if (StringUtils.hasLength(request.getCoordinate())) {

        }

        if (StringUtils.hasLength(request.getFilter())) {

        }

        return builder.build().toUriString();
    }


}
