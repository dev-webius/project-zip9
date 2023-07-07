package com.zip9.api.LH.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zip9.api.LH.dto.*;
import com.zip9.api.LH.enums.AnnouncementStatus;
import com.zip9.api.LH.enums.AnnouncementType;
import com.zip9.api.LH.enums.City;
import com.zip9.api.announcement.dto.AnnouncementDetailRequest;
import com.zip9.api.announcement.dto.AnnouncementSupplyInfoRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class LHApiClient {
    private final WebClient webClient;
    private static final String BASE_URL = "http://apis.data.go.kr/B552555";
    private static final String DECODED_SERVICE_KEY = "dFO37hTXbj0XfqChs155Oc8em7iRWRtqQYi9kT54LWZSjjBIErEr4sEYwfKn8wIAisL3B8MUYIggAKSrxZXIPA==";
    private static final String PUBLIC_RENTAL_ANNOUNCEMENT_LIST_URI = "/lhLeaseNoticeInfo1/lhLeaseNoticeInfo1";
    private static final String PUBLIC_RENTAL_ANNOUNCEMENT_DETAIL_URI = "/lhLeaseNoticeDtlInfo1/getLeaseNoticeDtlInfo1";
    private static final String PUBLIC_RENTAL_ANNOUNCEMENT_SUPPLY_URI = "/lhLeaseNoticeSplInfo1/getLeaseNoticeSplInfo1";

    public LHApiClient() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(BASE_URL)
                .build();
    }

    public List<LHAnnouncementResponse> getAnnouncements(LHAnnouncementRequest request) {
        List<Object> response = webClient
                .get()
                .uri(buildUriWithQueryParmas(request))
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block();

        LHAnnouncementListResponse lhAnnouncementsResponse = new LHAnnouncementListResponse();

        if (((Map)response.get(1)).containsKey("dsList")) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            lhAnnouncementsResponse = mapper.convertValue(response.get(1), LHAnnouncementListResponse.class);
        }

        return lhAnnouncementsResponse.getLhAnnouncements();
    }


    public LHAnnouncementSupplyInfoResponse getAnnouncementSupplyInfo(AnnouncementSupplyInfoRequest request) {
        Flux<Object> flux = webClient
                .get()
                .uri(buildUriWithQueryParmas(request))
                .retrieve()
                .bodyToFlux(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return mapper.convertValue(flux.collectList().block().get(1), LHAnnouncementSupplyInfoResponse.class);
    }

    public LHAnnouncementDetailResponse getAnnouncementDetail(AnnouncementDetailRequest request) {
        Flux<Object> flux = webClient
                .get()
                .uri(buildUriWithQueryParmas(request))
                .retrieve()
                .bodyToFlux(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return mapper.convertValue(flux.collectList().block().get(1), LHAnnouncementDetailResponse.class);
    }


    private String buildUriWithQueryParmas(LHAnnouncementRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath(PUBLIC_RENTAL_ANNOUNCEMENT_LIST_URI)
                .queryParam("serviceKey", DECODED_SERVICE_KEY)
                .queryParam("PG_SZ", request.getSize())
                .queryParam("PAGE", 1)
                .queryParam("PAN_NM", request.getTitle());

        if (!ObjectUtils.isEmpty(request.getAnnouncementType())) {
            builder.queryParam("UPP_AIS_TP_CD", AnnouncementType.valueOf(request.getAnnouncementType().toUpperCase()).code);
        }

        if (!ObjectUtils.isEmpty(request.getCity())) {
            builder.queryParam("CNP_CD", City.valueOf(request.getCity().toUpperCase()).code);
        }

        if (!ObjectUtils.isEmpty(request.getAnnouncementStatus())) {
            builder.queryParam("PAN_SS", AnnouncementStatus.valueOf(request.getAnnouncementStatus().toUpperCase()).code);
        }

        if (!ObjectUtils.isEmpty(request.getRegistStartDate())) {
            builder.queryParam("PAN_ST_DT", request.getRegistStartDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }

        if (!ObjectUtils.isEmpty(request.getRegistEndDate())) {
            builder.queryParam("PAN_ED_DT", request.getRegistEndDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }

        if (!ObjectUtils.isEmpty(request.getCloseStartDate())) {
            builder.queryParam("CLSG_ST_DT", request.getCloseStartDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }

        if (!ObjectUtils.isEmpty(request.getCloseEndDate())) {
            builder.queryParam("CLSG_ED_DT", request.getCloseEndDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }

        return builder.build().toUriString();
    }

    private String buildUriWithQueryParmas(AnnouncementDetailRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath(PUBLIC_RENTAL_ANNOUNCEMENT_DETAIL_URI)
                .queryParam("serviceKey", DECODED_SERVICE_KEY)
                .queryParam("PAN_ID", request.getAnnouncementId())
                .queryParam("UPP_AIS_TP_CD", request.getAnnouncementTypeCode())
                .queryParam("SPL_INF_TP_CD", request.getSupplyTypeCode())
                .queryParam("CCR_CNNT_SYS_DS_CD", request.getCrmCode());

        if (!ObjectUtils.isEmpty(request.getAnnouncementTypeCode())) {
            builder.queryParam("AIS_TP_CD", request.getAnnouncementDetailTypeCode());
        }

        return builder.build().toUriString();
    }

    private String buildUriWithQueryParmas(AnnouncementSupplyInfoRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath(PUBLIC_RENTAL_ANNOUNCEMENT_SUPPLY_URI)
                .queryParam("serviceKey", DECODED_SERVICE_KEY)
                .queryParam("PAN_ID", request.getAnnouncementId())
                .queryParam("UPP_AIS_TP_CD", request.getAnnouncementTypeCode())
                .queryParam("SPL_INF_TP_CD", request.getSupplyTypeCode())
                .queryParam("CCR_CNNT_SYS_DS_CD", request.getCrmCode());

        if (!ObjectUtils.isEmpty(request.getAnnouncementTypeCode())) {
            builder.queryParam("AIS_TP_CD", request.getAnnouncementDetailTypeCode());
        }

        return builder.build().toUriString();
    }
}
