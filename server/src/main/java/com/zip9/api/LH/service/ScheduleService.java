package com.zip9.api.LH.service;

import com.zip9.api.LH.dto.*;
import com.zip9.api.LH.entity.RawDataEntity;
import com.zip9.api.LH.enums.RawDataStatus;
import com.zip9.api.LH.repository.RawDataRepository;
import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.announcement.dto.AnnouncementsMigrationRequest;
import com.zip9.api.announcement.entity.*;
import com.zip9.api.announcement.service.AnnouncementDBService;
import com.zip9.api.common.util.BizStringUtlls;
import com.zip9.api.naver.dto.GeocodingResponse;
import com.zip9.api.naver.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final LHService lhService;
    private final AnnouncementDBService announcementDBService;
    private final RawDataRepository rawDataRepository;
    private final GeocodingService geocodingService;

    /**
     * 마이그레이션 스케쥴러
     *
     * ex)  2023-07-18 00:00:00 스케줄러 실행 시
     *      2023-07-17 00:00:00 ~ 2023-07-17 23:59:59 대상
     */
    @Transactional
    public boolean migration(AnnouncementsMigrationRequest request) {
        List<LHAnnouncementRequest> requests = request.buildLHRequests();

        // STEP 1 - OPEN API RAW DATA 저장
        List<LHAnnouncementResponse> lhAnnouncements = requests.stream()
                .map(lhService::searchAnnouncements)
                .flatMap(List::stream)
                .toList()
                .stream()
                .sorted(Comparator.comparing(LHAnnouncementResponse::getRegistDate).reversed())
                .toList();
        STEP_1(lhAnnouncements);

        // STEP 2 - RAW DATA를 DB 칼럼에 맞춰 입력
        List<RawDataEntity> rawDataEntities = rawDataRepository.findAllByStatusEqualsIgnoreCaseAndCreatedAtAfter(RawDataStatus.STEP_1_COMPLETED.code, request.getFromDatetime());
        STEP_2(rawDataEntities);

        // STEP 3 - 마감기간 지난 공고 마감 처리
        List<AnnouncementEntity> notClosedAnnouncements = announcementDBService.getNotClosedAnnouncements(request.getFromDatetime());
        STEP_3(notClosedAnnouncements);

        return true;
    }

    private void STEP_3(List<AnnouncementEntity> notClosedAnnouncements) {
        for (AnnouncementEntity announcement : notClosedAnnouncements) {
            announcement.close();
        }
    }

    private void STEP_2(List<RawDataEntity> rawDataEntities) {
        for (RawDataEntity rawData : rawDataEntities) {
            LHAnnouncementResponse lhAnnouncement = LHAnnouncementResponse.jsonToObject(rawData.getAnnouncement());
            LHAnnouncementDetailResponse lhAnnouncementDetail = LHAnnouncementDetailResponse.jsonToObject(rawData.getAnnouncementDetail());
            LHAnnouncementSupplyInfoResponse lhAnnouncementSupplyInfo = LHAnnouncementSupplyInfoResponse.jsonToObject(rawData.getAnnouncementSupply());

            AnnouncementDetailResponse announcementDetail = buildAnnouncementDetailsFrom(lhAnnouncementDetail, lhAnnouncementSupplyInfo, lhAnnouncement.getSupplyTypeCode());

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

            // 신청자격 저장
            for (AnnouncementDetailResponse.Qualification qualification : announcementDetail.getQualifications()) {
                announcementDBService.save(QualificationEntity.ByAnnouncementDetailQualificationBuilder()
                        .qualification(qualification)
                        .announcement(announcementEntity)
                        .build());
            }

            // 공급일정 저장
            for (AnnouncementDetailResponse.SupplySchedule supplySchedule : announcementDetail.getSupplySchedules()) {
                announcementDBService.save(SupplyScheduleEntity.ByAnnouncementDetailSupplyScheduleBuilder()
                        .supplySchedule(supplySchedule)
                        .announcement(announcementEntity)
                        .build()
                );
            }

            // 주택단지 저장
            for (AnnouncementDetailResponse.HouseComplex houseComplex : announcementDetail.getHouseComplexes()) {
                // 주택단지 저장
                HouseComplexEntity houseComplexEntity = announcementDBService.save(HouseComplexEntity.ByAnnouncementDetailHouseComplexBuilder()
                        .houseComplex(houseComplex)
                        .announcement(announcementEntity)
                        .build()
                );

                // 주택단지 위치정보 저장
                GeocodingResponse.Address address = getHouseComplexAddress(houseComplex);
                announcementDBService.save(HouseComplexPositionEntity.ByAnnouncementDetailHouseComplexPositionBuilder()
                        .address(address)
                        .houseComplex(houseComplexEntity)
                        .build()
                );

                // 주택단지별 주택타입 저장
                for (AnnouncementDetailResponse.HouseType houseType : houseComplex.getHouseTypes()) {
                    announcementDBService.save(HouseTypeEntity.ByAnnouncementDetailHouseTypeBuilder()
                            .houseType(houseType)
                            .houseComplex(houseComplexEntity)
                            .build()
                    );
                }

                // 주택단지별 첨부파일 저장
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

    private AnnouncementDetailResponse buildAnnouncementDetailsFrom(LHAnnouncementDetailResponse lhDetail, LHAnnouncementSupplyInfoResponse lhSupplyInfo, String houseSupplyTypeCode) {
        List<AnnouncementDetailResponse.HouseComplex> houseComplexes = buildHouseComplexesFrom(lhDetail, lhSupplyInfo);
        List<AnnouncementDetailResponse.SupplySchedule> supplySchedules = buildSupplySchedulesFrom(lhDetail, houseSupplyTypeCode);

        AnnouncementDetailResponse.Reception reception = AnnouncementDetailResponse.Reception.buildFrom(lhDetail.getReception());
        AnnouncementDetailResponse.Etc etc = AnnouncementDetailResponse.Etc.buildFrom(lhDetail.getEtcInfo());
        List<AnnouncementDetailResponse.Qualification> qualifications = AnnouncementDetailResponse.Qualification.buildFrom(lhDetail.getQualifications());

        return AnnouncementDetailResponse.builder()
                .supplySchedules(supplySchedules)
                .houseComplexes(houseComplexes)
                .reception(reception)
                .etc(etc)
                .qualifications(qualifications)
                .build();
    }

    private List<AnnouncementDetailResponse.SupplySchedule> buildSupplySchedulesFrom(LHAnnouncementDetailResponse lhDetail, String houseSupplyTypeCode) {
        List<AnnouncementDetailResponse.SupplySchedule> supplySchedules = new ArrayList<>();

        for (LHAnnouncementDetailResponse.SupplySchedule.Value lhDetailSupplyScheduleValue : lhDetail.getSupplySchedule().getValues()) {
            String houseComplexName = "";
            if (StringUtils.hasLength(lhDetailSupplyScheduleValue.getHouseComplexName())) {
                houseComplexName = lhDetailSupplyScheduleValue.getHouseComplexName();
            } else {
                houseComplexName = lhDetail.getHouseComplex().getValues().stream()
                        .filter(houseComplexValue -> houseComplexValue.getHouseComplexName().equals(lhDetailSupplyScheduleValue.getHouseComplexName()))
                        .findAny()
                        .map(LHAnnouncementDetailResponse.HouseComplex.Value::getHouseComplexDetailAddress)
                        .orElse("");
            }

            supplySchedules.add(AnnouncementDetailResponse.SupplySchedule.builder()
                    .target(LHAnnouncementDetailResponse.SupplySchedule.existTargetOf(houseSupplyTypeCode)
                            ? lhDetailSupplyScheduleValue.getTarget()
                            : houseComplexName
                    )
                    .applicationDatetime(lhDetailSupplyScheduleValue.getApplicationDatetime())
                    .applicationMethod(lhDetailSupplyScheduleValue.getApplicationMethod())
                    .winnerAnnouncementDate(lhDetailSupplyScheduleValue.getWinnerAnnouncementDate())
                    .paperSubmitOpenAnnouncementDate(lhDetailSupplyScheduleValue.getPaperSubmitOpenAnnouncementDate())
                    .paperSubmitTerm(BizStringUtlls.makeTermValueFrom(lhDetailSupplyScheduleValue.getWinnerPaperSubmitStartDate(), lhDetailSupplyScheduleValue.getWinnerPaperSubmitEndDate()))
                    .contractTerm(BizStringUtlls.makeTermValueFrom(lhDetailSupplyScheduleValue.getContractStartDate(), lhDetailSupplyScheduleValue.getContractEndDate()))
                    .applicationTerm(BizStringUtlls.makeTermValueFrom(lhDetailSupplyScheduleValue.getApplicationStartDate(), lhDetailSupplyScheduleValue.getApplicationEndDate()))
                    .houseBrowseTerm(BizStringUtlls.makeTermValueFrom(lhDetailSupplyScheduleValue.getHouseBrowseStartDate(), lhDetailSupplyScheduleValue.getHouseBrowseEndDate()))
                    .supplyScheduleGuide(lhDetailSupplyScheduleValue.getSupplyScheduleGuide())
                    .build()
            );
        }

        return supplySchedules;
    }

    private List<AnnouncementDetailResponse.HouseComplex> buildHouseComplexesFrom(LHAnnouncementDetailResponse lhDetail, LHAnnouncementSupplyInfoResponse lhSupplyInfo) {
        List<AnnouncementDetailResponse.HouseComplex> houseComplexes = new ArrayList<>();

        for (LHAnnouncementDetailResponse.HouseComplex.Value lhDetailHouseComplexValue : lhDetail.getHouseComplex().getValues()) {
            AnnouncementDetailResponse.HouseComplex houseComplex = AnnouncementDetailResponse.HouseComplex.buildFrom(lhDetailHouseComplexValue);
            List<AnnouncementDetailResponse.HouseType> houseTypes = AnnouncementDetailResponse.HouseType.buildFrom(lhSupplyInfo, lhDetailHouseComplexValue.getHouseComplexName());
            houseComplex.setHouseTypes(houseTypes);
            houseComplexes.add(houseComplex);
        }

        return houseComplexes;
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
}
