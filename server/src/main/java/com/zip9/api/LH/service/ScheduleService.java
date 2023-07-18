package com.zip9.api.LH.service;

import com.zip9.api.LH.dto.*;
import com.zip9.api.LH.entity.RawDataEntity;
import com.zip9.api.LH.enums.RawDataStatus;
import com.zip9.api.LH.repository.RawDataRepository;
import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.announcement.dto.AnnouncementRequest;
import com.zip9.api.announcement.entity.*;
import com.zip9.api.announcement.service.AnnouncementDBService;
import com.zip9.api.announcement.service.AnnouncementOpenAPIService;
import com.zip9.api.naver.dto.GeocodingResponse;
import com.zip9.api.naver.service.GeocodingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final LHService lhService;
    private final AnnouncementDBService announcementDBService;
    private final AnnouncementOpenAPIService announcementOpenAPIService;
    private final RawDataRepository rawDataRepository;
    private final GeocodingService geocodingService;

    /**
     * 마이그레이션 스케쥴러
     */
    @Transactional(readOnly = false)
    public void migration() {
        List<LHAnnouncementRequest> requests = AnnouncementRequest.builder()
                .registStartDate(LocalDate.parse("2022-07-04"))
                .registEndDate(LocalDate.parse("2023-07-04"))
                .build()
                .buildLHRequests();

        List<LHAnnouncementResponse> lhAnnouncements = requests.stream()
                .map(lhService::searchAnnouncements)
                .flatMap(List::stream)
                .toList()
                .stream()
                .sorted(Comparator.comparing(LHAnnouncementResponse::getRegistDate).reversed())
                .toList();

        // STEP 1 - OPEN API RAW DATA 저장
        STEP_1(lhAnnouncements);

        // STEP 2 - RAW DATA를 DB 칼럼에 맞춰 입력
        List<RawDataEntity> rawDataEntities = rawDataRepository.findAllByStatusEqualsIgnoreCaseAndCreatedAtAfter(RawDataStatus.STEP_1_COMPLETED.code, LocalDateTime.of(LocalDate.now(), LocalTime.MIN));

        STEP_2(rawDataEntities);
    }

    private void STEP_2(List<RawDataEntity> rawDataEntities) {
        for (RawDataEntity rawData : rawDataEntities) {
            LHAnnouncementResponse lhAnnouncement = LHAnnouncementResponse.jsonToObject(rawData.getAnnouncement());
            LHAnnouncementDetailResponse lhAnnouncementDetail = LHAnnouncementDetailResponse.jsonToObject(rawData.getAnnouncementDetail());
            LHAnnouncementSupplyInfoResponse lhAnnouncementSupplyInfo = LHAnnouncementSupplyInfoResponse.jsonToObject(rawData.getAnnouncementSupply());

            AnnouncementDetailResponse announcementDetail = announcementOpenAPIService.buildAnnouncementDetailsFrom(lhAnnouncementDetail, lhAnnouncementSupplyInfo, lhAnnouncement.getSupplyTypeCode());

            // 공고 저장
            AnnouncementEntity announcementEntity = announcementDBService.save(AnnouncementEntity.ByLHAnnouncementBuilder()
                    .lhAnnouncement(lhAnnouncement)
                    .build()
            );

            // 접수처 저장
            announcementDBService.save(ReceptionEntity.ByAnnouncementDetailReceptionBuilder()
                    .reception(announcementDetail.getReception())
                    .announcement(announcementEntity)
                    .build()
            );

            // 기타 저장
            announcementDBService.save(EtcEntity.ByAnnouncementDetailEtcBuilder()
                    .etc(announcementDetail.getEtc())
                    .announcement(announcementEntity)
                    .build()
            );

            // 공급일정 저장
            for (AnnouncementDetailResponse.SupplySchedule supplySchedule : announcementDetail.getSupplySchedules()) {
                announcementDBService.save(SupplyScheduleEntity.ByAnnouncementDetailSupplyScheduleBuilder()
                        .supplySchedule(supplySchedule)
                        .announcement(announcementEntity)
                        .build()
                );
            }

            // 주택단지 저장
            AnnouncementDetailResponse.HouseComplexes houseComplexes = announcementDetail.getHouseComplexes();
            Map<String, AnnouncementDetailResponse.HouseComplex> map = houseComplexes.getMap();
            for (String key : map.keySet()) {
                AnnouncementDetailResponse.HouseComplex houseComplex = map.get(key);

                // 주택단지 저장
                HouseComplexEntity houseComplexEntity = announcementDBService.save(HouseComplexEntity.ByAnnouncementDetailHouseComplexBuilder()
                        .houseComplex(houseComplex)
                        .announcement(announcementEntity)
                        .build()
                );

                // 주택 단지 위치정보 저장
                GeocodingResponse.Address address = getHouseComplexAddress(houseComplex);
                announcementDBService.save(HouseComplexPositionEntity.ByAnnouncementDetailHouseComplexPositionBuilder()
                        .address(address)
                        .houseComplex(houseComplexEntity)
                        .build()
                );

                // 주택 단지별 주택타입 저장
                for (AnnouncementDetailResponse.HouseType houseType : houseComplex.getHouseTypes()) {
                    announcementDBService.save(HouseTypeEntity.ByAnnouncementDetailHouseTypeBuilder()
                            .houseType(houseType)
                            .houseComplex(houseComplexEntity)
                            .build()
                    );
                }

                // 주택 단지별 첨부파일 저장
                for (AnnouncementDetailResponse.Attachment attachment : houseComplex.getAttachments()) {
                    announcementDBService.save(HouseComplexAttachmentEntity.ByAnnouncementDetailAttachmentBuilder()
                            .attachment(attachment)
                            .houseComplex(houseComplexEntity)
                            .build()
                    );
                }
            }

            rawData.setStatus(RawDataStatus.STEP_2_COMPLETED.code);
        }
    }

    private GeocodingResponse.Address getHouseComplexAddress(AnnouncementDetailResponse.HouseComplex houseComplex) {
        GeocodingResponse.Address address = new GeocodingResponse.Address();

        if (StringUtils.hasLength(houseComplex.getName())) {
            address = geocodingService.findAddress(houseComplex.getName());
        }

        if (!address.hasPosition() && StringUtils.hasLength(houseComplex.getDetailAddress())) {
            address = geocodingService.findAddress(houseComplex.getDetailAddress());
        }

        if (!address.hasPosition() && StringUtils.hasLength(houseComplex.getAddress() + " " + houseComplex.getDetailAddress())) {
            address = geocodingService.findAddress(houseComplex.getAddress() + " " + houseComplex.getDetailAddress());
        }

        if (!address.hasPosition() && StringUtils.hasLength(houseComplex.getAddress())) {
            address = geocodingService.findAddress(houseComplex.getAddress());
        }

        return address;
    }

    private void STEP_1(List<LHAnnouncementResponse> lhAnnouncements) {
        List<RawDataEntity> rawDataEntities = lhAnnouncements.stream()
                .map(lhAnnouncement -> RawDataEntity.ByLHAnnouncementBuilder()
                        .announcementId(lhAnnouncement.getId())
                        .lhAnnouncement(lhAnnouncement)
                        .lhAnnouncementDetail(lhService.getAnnouncementDetail(
                                LHAnnouncementDetailAndSupplyRequest.ByLHAnnouncementBuilder()
                                        .lhAnnouncement(lhAnnouncement)
                                        .build()
                        ))
                        .lhSupplyInfo(lhService.getAnnouncementSupplyInfo(
                                LHAnnouncementDetailAndSupplyRequest.ByLHAnnouncementBuilder()
                                        .lhAnnouncement(lhAnnouncement)
                                        .build()
                        ))
                        .status(RawDataStatus.STEP_1_COMPLETED.code)
                        .build()
                ).toList();

        rawDataRepository.saveAll(rawDataEntities);
    }


    /**
     * 최초 마이그레이션
     */
    @Transactional(readOnly = false)
    public void initialMigration() {
        // 공고등록일 2022-01-01 ~ 2023-12-31
        // 공고마감일 오늘 이후
        List<LHAnnouncementRequest> requests = AnnouncementRequest.builder()
                .registStartDate(LocalDate.parse("2022-01-01"))
                .registEndDate(LocalDate.parse("2023-07-12"))
                .closeStartDate(LocalDate.now())
                .build()
                .buildLHRequests();

        List<LHAnnouncementResponse> lhAnnouncements = requests.stream()
                .map(lhService::searchAnnouncements)
                .flatMap(List::stream)
                .toList()
                .stream()
                .sorted(Comparator.comparing(LHAnnouncementResponse::getRegistDate).reversed())
                .toList();

        lhAnnouncements.forEach(this::save);
    }

    private AnnouncementEntity save(LHAnnouncementResponse lhAnnouncement) {
        return announcementDBService.save(AnnouncementEntity.ByLHAnnouncementBuilder()
                .lhAnnouncement(lhAnnouncement)
                .build()
        );
    }
}
