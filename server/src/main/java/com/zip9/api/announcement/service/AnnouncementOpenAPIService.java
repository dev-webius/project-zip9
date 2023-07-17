package com.zip9.api.announcement.service;

import com.zip9.api.LH.dto.LHAnnouncementDetailAndSupplyRequest;
import com.zip9.api.LH.dto.LHAnnouncementDetailResponse;
import com.zip9.api.LH.dto.LHAnnouncementResponse;
import com.zip9.api.LH.dto.LHAnnouncementSupplyInfoResponse;
import com.zip9.api.LH.enums.HouseSupplyType;
import com.zip9.api.LH.service.LHService;
import com.zip9.api.announcement.dto.*;
import com.zip9.api.naver.dto.GeocodingResponse;
import com.zip9.api.naver.service.GeocodingService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementOpenAPIService implements AnnouncementReadService {
    private LHService lhService;
    private GeocodingService geocodingService;


    /**
     * 공고 목록 조회
     */
    @Override
    public AnnouncementResponse getAnnouncements(AnnouncementRequest request) {
        // LH 공고 리스트
        List<LHAnnouncementResponse> lhAnnouncements = request.buildLHRequests().stream()
                .map(lhRequest -> lhService.searchAnnouncements(lhRequest))
                .flatMap(List::stream)
                .toList()
                .stream()
                .sorted(Comparator.comparing(LHAnnouncementResponse::getRegistDate).reversed())
                .toList();

        // 공고 응답 데이터 생성 - 공고 내 단지별 위치정보 조회
        List<Announcement> announcements = lhAnnouncements.stream()
                .map(lhAnnouncement -> {
                            // 공고 상세 조회
                            LHAnnouncementDetailResponse lhAnnouncementDetail = lhService.getAnnouncementDetail(
                                    LHAnnouncementDetailAndSupplyRequest.ByLHAnnouncementBuilder()
                                            .lhAnnouncement(lhAnnouncement)
                                            .build()
                            );

                            // 응답 데이터 생성
                            return Announcement.ByLHAnnouncement()
                                    .lhAnnouncement(lhAnnouncement)
                                    .positions(buildPositionOfHouesComplex(lhAnnouncementDetail))
                                    .build();
                        }
                )
                .toList();

        // API Response 데이터 생성
        AnnouncementResponse response = new AnnouncementResponse();


        for (Announcement announcement : announcements) {
            Map<String, Integer> numberOfAnnouncementsByCity = response.getMeta().getNumberOfAnnouncementsByCity();

            // 도시별 공고 추가
            response.getItem().getAnnouncements().get(announcement.getCityShortName()).add(announcement);

            // 도시별 공고수 증가
            numberOfAnnouncementsByCity.put(
                    announcement.getCityShortName(),
                    numberOfAnnouncementsByCity.get(announcement.getCityShortName()) + 1
            );
        }

        return response;
    }

    public List<AnnouncementResponse.Position> buildPositionOfHouesComplex(LHAnnouncementDetailResponse lhAnnouncementDetail) {
        List<AnnouncementDetailResponse.HouseComplex> houseComplexes = lhAnnouncementDetail.getHouseComplex().getValues().stream()
                .map(AnnouncementDetailResponse.HouseComplex::buildFrom)
                .toList();

        return houseComplexes.stream().map(this::buildPositionOfHouseComplex).toList();
    }

    private AnnouncementResponse.Position buildPositionOfHouseComplex(AnnouncementDetailResponse.HouseComplex houseComplex) {
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

        if (address.hasPosition()) {
            return AnnouncementResponse.Position.builder()
                    .houseComplexName(houseComplex.getNameOrDetailAddress())
                    .address(address.getAddress())
                    .x(address.getX())
                    .y(address.getY())
                    .build();
        } else {
            return new AnnouncementResponse.Position();
        }
    }

    /**
     * 공고 상세정보 조회
     */
    @Override
    public AnnouncementDetailResponse getAnnouncementDetail(AnnouncementDetailRequest request) {
        LHAnnouncementDetailResponse detail = lhService.getAnnouncementDetail(LHAnnouncementDetailAndSupplyRequest.ByAnnouncementDetailRequestBuilder().request(request).build());
        LHAnnouncementSupplyInfoResponse supplyInfo = lhService.getAnnouncementSupplyInfo(LHAnnouncementDetailAndSupplyRequest.ByAnnouncementDetailRequestBuilder().request(request).build());

        return buildAnnouncementDetailsFrom(detail, supplyInfo, HouseSupplyType.valueOf(request.getSupplyType()).code);
    }

    public AnnouncementDetailResponse buildAnnouncementDetailsFrom(LHAnnouncementDetailResponse lhDetail, LHAnnouncementSupplyInfoResponse lhSupplyInfo, String houseSupplyTypeCode) {
        List<AnnouncementDetailResponse.HouseComplex> houseComplexes = buildHouseComplexesFrom(lhDetail, lhSupplyInfo);
        List<AnnouncementDetailResponse.SupplySchedule> supplySchedules = buildSupplySchedulesFrom(lhDetail, houseSupplyTypeCode);

        // 데이터 보정
        Map<String, AnnouncementDetailResponse.HouseComplex> houseComplexesMap = houseComplexes.stream()
                .collect(
                        Collectors.toMap(
                                AnnouncementDetailResponse.HouseComplex::getNameOrDetailAddress,
                                houseComplex -> houseComplex
                        )
                );

        return AnnouncementDetailResponse.builder()
                .supplySchedules(supplySchedules)
                .houseComplexes(AnnouncementDetailResponse.HouseComplexes.builder()
                        .names(houseComplexesMap.keySet().stream().toList())
                        .map(houseComplexesMap)
                        .build())
                .reception(buildReceptionFrom(lhDetail))
                .attachments(buildAttachmentsFrom(lhDetail))
                .etc(buildEtcFrom(lhDetail))
                .build();
    }

    private List<AnnouncementDetailResponse.Attachment> buildAttachmentsFrom(LHAnnouncementDetailResponse lhDetail) {
        return lhDetail.getAttachment().getValues().stream()
                .map(lhDetailAttachment -> AnnouncementDetailResponse.Attachment.builder()
                        .fileTypeName(lhDetailAttachment.getFileTypeName())
                        .fileName(lhDetailAttachment.getFileName())
                        .downloadUrl(lhDetailAttachment.getDownloadUrl())
                        .build()
                )
                .toList();
    }

    private List<AnnouncementDetailResponse.SupplySchedule> buildSupplySchedulesFrom(LHAnnouncementDetailResponse lhDetail, String houseSupplyTypeCode) {
        List<AnnouncementDetailResponse.SupplySchedule> supplySchedules = new ArrayList<>();

        LHAnnouncementDetailResponse.SupplySchedule.Label lhDetailSupplyScheduleLabel = lhDetail.getSupplySchedule().getLabel();

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
                    .paperSubmitTerm(makeTermValueFrom(lhDetailSupplyScheduleValue.getWinnerPaperSubmitStartDate(), lhDetailSupplyScheduleValue.getWinnerPaperSubmitEndDate()))
                    .contractTerm(makeTermValueFrom(lhDetailSupplyScheduleValue.getContractStartDate(), lhDetailSupplyScheduleValue.getContractEndDate()))
                    .applicationTerm(makeTermValueFrom(lhDetailSupplyScheduleValue.getApplicationStartDate(), lhDetailSupplyScheduleValue.getApplicationEndDate()))
                    .houseBrowseTerm(makeTermValueFrom(lhDetailSupplyScheduleValue.getHouseBrowseStartDate(), lhDetailSupplyScheduleValue.getHouseBrowseEndDate()))
                    .supplyScheduleGuide(lhDetailSupplyScheduleValue.getSupplyScheduleGuide())
                    .build()
            );
        }

        return supplySchedules;
    }

    private AnnouncementDetailResponse.Etc buildEtcFrom(LHAnnouncementDetailResponse lhDetail) {
        LHAnnouncementDetailResponse.Etc etc = lhDetail.getEtcInfo();
        LHAnnouncementDetailResponse.Etc.Value lhEtcValue = etc.getValues().stream()
                .findFirst()
                .orElse(new LHAnnouncementDetailResponse.Etc.Value());

        return AnnouncementDetailResponse.Etc.builder()
                .announcementDescription(lhEtcValue.getAnnouncementDescription())
                .comment(lhEtcValue.getComment())
                .groupHomeAgency(lhEtcValue.getGroupHomeAgency())
                .correctOrCancelReason(lhEtcValue.getCorrectOrCancelReason())
                .targetArea(lhEtcValue.getTargetArea())
                .targetHouse(lhEtcValue.getTargetHouse())
                .leaseTerms(lhEtcValue.getLeaseTerms())
                .leaseCondition(lhEtcValue.getLeaseCondition())
                .caution(lhEtcValue.getCaution())
                .supportLimitAmount(NumberUtils.toInt(lhEtcValue.getSupportLimitAmount(), 0))
                .numberOfSupplyHousehold(NumberUtils.toInt(lhEtcValue.getNumberOfSupplyHousehold(), 0))
                .receptionAddress(lhEtcValue.getReceptionAddress())
                .build();
    }

    private AnnouncementDetailResponse.Reception buildReceptionFrom(LHAnnouncementDetailResponse lhDetail) {
        LHAnnouncementDetailResponse.Reception reception = lhDetail.getReception();
        LHAnnouncementDetailResponse.Reception.Value receptionValue = reception.getValues().stream()
                .findFirst()
                .orElse(new LHAnnouncementDetailResponse.Reception.Value());

        return AnnouncementDetailResponse.Reception.builder()
                .address(receptionValue.getAddress())
                .telephoneNumber(receptionValue.getTelephoneNumber())
                .operationTerm(makeTermValueFrom(receptionValue.getOpenDate(), receptionValue.getCloseDate()))
                .scheduleGuide(receptionValue.getScheduleGuide())
                .receptionGuide(receptionValue.getReceptionGuide())
                .build();
    }

    private List<AnnouncementDetailResponse.HouseComplex> buildHouseComplexesFrom(LHAnnouncementDetailResponse lhDetail, LHAnnouncementSupplyInfoResponse lhSupplyInfo) {
        List<AnnouncementDetailResponse.HouseComplex> houseComplexes = new ArrayList<>();

        for (LHAnnouncementDetailResponse.HouseComplex.Value lhDetailHouseComplexValue : lhDetail.getHouseComplex().getValues()) {
            AnnouncementDetailResponse.HouseComplex houseComplex = AnnouncementDetailResponse.HouseComplex.buildFrom(lhDetailHouseComplexValue);

            List<AnnouncementDetailResponse.HouseType> houseTypes = buildHouseTypesFrom(lhSupplyInfo, lhDetailHouseComplexValue.getHouseComplexName());
            List<AnnouncementDetailResponse.Attachment> attachments = buildAttachmentsFrom(lhDetail.getHouseComplexAttachment(), lhDetailHouseComplexValue.getHouseComplexName());

            houseComplex.setHouseTypes(houseTypes);
            houseComplex.setAttachments(attachments);

            houseComplexes.add(houseComplex);
        }

        return houseComplexes;
    }

    private List<AnnouncementDetailResponse.Attachment> buildAttachmentsFrom(LHAnnouncementDetailResponse.HouseComplexAttachment lhDetailHouseComplexAttachment, String houseComplexName) {
        return lhDetailHouseComplexAttachment.getValues().stream()
                .filter(lhDetailHouseComplexAttachmentValue -> isEqualsIgnoringWhitespaces(lhDetailHouseComplexAttachmentValue.getHouseComplexName(), houseComplexName))
                .map(AnnouncementDetailResponse.Attachment::buildFrom)
                .toList();
    }

    private List<AnnouncementDetailResponse.HouseType> buildHouseTypesFrom(LHAnnouncementSupplyInfoResponse lhSupplyInfo, String houseComplexName) {
        LHAnnouncementSupplyInfoResponse.Label lhSupplyInfoLabel = lhSupplyInfo.getLabel();

        return lhSupplyInfo.getValues().stream()
                .filter(lhSupplyInfoValue -> isEqualsIgnoringWhitespaces(lhSupplyInfoValue.getHouseComplexName(), houseComplexName))
                .map(AnnouncementDetailResponse.HouseType::buildFrom)
                .toList();
    }

    private boolean isEqualsIgnoringWhitespaces(String s1, String s2) {
        return s1.replaceAll("\\s", "").equals(s2.replaceAll("\\s", ""));
    }

    private String makeTermValueFrom(String startDate, String endDate) {
        if (StringUtils.hasLength(startDate) && StringUtils.hasLength(endDate)) {
            return startDate + " ~ " + endDate;
        } else {
            return "";
        }
    }
}
