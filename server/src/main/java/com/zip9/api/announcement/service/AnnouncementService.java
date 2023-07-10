package com.zip9.api.announcement.service;

import com.zip9.api.LH.dto.LHAnnouncementDetailResponse;
import com.zip9.api.LH.dto.LHAnnouncementRequest;
import com.zip9.api.LH.dto.LHAnnouncementResponse;
import com.zip9.api.LH.dto.LHAnnouncementSupplyInfoResponse;
import com.zip9.api.LH.enums.City;
import com.zip9.api.LH.service.LHService;
import com.zip9.api.announcement.dto.*;
import com.zip9.api.naver.dto.GeocodingResponse;
import com.zip9.api.naver.service.GeocodingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementService {
    private LHService lhService;
    private GeocodingService geocodingService;

    /**
     * 공고 목록 조회
     */
    public AnnouncementResponse searchAnnouncements(AnnouncementRequest request) {
        // LH 공고 리스트
        List<LHAnnouncementRequest> lhAnnouncementRequests = request.buildLHRequests();
        List<LHAnnouncementResponse> lhAnnouncements = lhAnnouncementRequests.stream()
                .map(lhRequest -> lhService.searchAnnouncements(lhRequest))
                .flatMap(List::stream)
                .toList()
                .stream()
                .sorted(Comparator.comparing(LHAnnouncementResponse::getRegistDate).reversed())
                .toList();

        // LH 공고상세 & 공급정보
        lhAnnouncements.forEach(lhAnnouncement -> {
            lhAnnouncement.setDetail(lhService.getAnnouncementDetail(new AnnouncementDetailRequest(lhAnnouncement)));
            lhAnnouncement.setSupplyInfo(lhService.getAnnouncementSupplyInfo(new AnnouncementSupplyInfoRequest(lhAnnouncement)));
        });

        // API Response 데이터 생성
        AnnouncementResponse response = new AnnouncementResponse();

        List<Announcement> announcements = lhAnnouncements.stream()
                .map(this::buildAnnouncementFrom)
                .toList();

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

    private Announcement buildAnnouncementFrom(LHAnnouncementResponse lhAnnouncement) {
        List<AnnouncementDetailsResponse> details = buildAnnouncementDetailsFrom(lhAnnouncement.getDetail(), lhAnnouncement.getSupplyInfo(), lhAnnouncement.getSupplyTypeCode());
        List<AnnouncementResponse.Position> positions = buildPositionOfHouseComplexes(details);

        Announcement announcement = Announcement.ByLHAnnouncement()
                .lhAnnouncement(lhAnnouncement)
                .positions(positions)
                .details(details)
                .build();


        if (!ObjectUtils.isEmpty(City.nameOf(lhAnnouncement.getCityName()))) {
            announcement.setCityShortName(City.nameOf(lhAnnouncement.getCityName()).shortName);
        }

        return announcement;
    }

    private List<AnnouncementResponse.Position> buildPositionOfHouseComplexes(List<AnnouncementDetailsResponse> details) {
        List<AnnouncementResponse.Position> positions = new ArrayList<>();

        for (AnnouncementDetailsResponse detail: details) {
            Map<String, AnnouncementDetailsResponse.HouseComplex> mapOfHouseComplexes = detail.getHouseComplexes().getMap();

            for (String key: mapOfHouseComplexes.keySet()) {
                AnnouncementDetailsResponse.HouseComplex houseComplex = mapOfHouseComplexes.get(key);
                GeocodingResponse.Address address = new GeocodingResponse.Address();

                if (StringUtils.hasLength(houseComplex.getName().getValue())) {
                    address = geocodingService.findAddress(houseComplex.getName().getValue());
                }

                if (!address.hasPosition() && StringUtils.hasLength(houseComplex.getDetailAddress().getValue())) {
                    address = geocodingService.findAddress(houseComplex.getDetailAddress().getValue());
                }

                if (!address.hasPosition() && StringUtils.hasLength(houseComplex.getAddress().getValue() + " " + houseComplex.getDetailAddress().getValue())) {
                    address = geocodingService.findAddress(houseComplex.getAddress().getValue() + " " + houseComplex.getDetailAddress().getValue());
                }

                if (!address.hasPosition() && StringUtils.hasLength(houseComplex.getAddress().getValue())) {
                    address = geocodingService.findAddress(houseComplex.getAddress().getValue());
                }

                if (address.hasPosition()) {
                    positions.add(AnnouncementResponse.Position.builder()
                            .houseComplexName(houseComplex.getNameOrDetailAddress())
                            .address(address.getAddress())
                            .x(address.getX())
                            .y(address.getY())
                            .build()
                    );
                }
            }
        }

        return positions;
    }

    private List<AnnouncementDetailsResponse> buildAnnouncementDetailsFrom(LHAnnouncementDetailResponse lhDetail, LHAnnouncementSupplyInfoResponse lhSupplyInfo, String houseSupplyTypeCode) {
        List<AnnouncementDetailsResponse> details = new ArrayList<>();

        List<AnnouncementDetailsResponse.HouseComplex> houseComplexes = buildHouseComplexesFrom(lhDetail, lhSupplyInfo);
        List<AnnouncementDetailsResponse.SupplySchedule> supplySchedules = buildSupplySchedulesFrom(lhDetail, houseSupplyTypeCode);

        // 데이터 보정
        Map<String, AnnouncementDetailsResponse.HouseComplex> houseComplexesMap = houseComplexes.stream()
                .collect(
                        Collectors.toMap(
                                AnnouncementDetailsResponse.HouseComplex::getNameOrDetailAddress,
                                houseComplex -> houseComplex
                )
        );

        details.add(AnnouncementDetailsResponse.builder()
                        .supplySchedules(supplySchedules)
                        .houseComplexes(AnnouncementDetailsResponse.HouseComplexes.builder()
                                .names(houseComplexesMap.keySet().stream().toList())
                                .map(houseComplexesMap)
                                .build())
                        .reception(buildReceptionFrom(lhDetail))
                        .etc(buildEtcFrom(lhDetail))
                        .build()
        );


        return details;
    }

    private List<AnnouncementDetailsResponse.SupplySchedule> buildSupplySchedulesFrom(LHAnnouncementDetailResponse lhDetail, String houseSupplyTypeCode) {
        List<AnnouncementDetailsResponse.SupplySchedule> supplySchedules = new ArrayList<>();

        LHAnnouncementDetailResponse.SupplySchedule.Label lhDetailSupplyScheduleLabel = lhDetail.getSupplySchedule().getLabel();

        for(LHAnnouncementDetailResponse.SupplySchedule.Value lhDetailSupplyScheduleValue : lhDetail.getSupplySchedule().getValues()) {
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

            supplySchedules.add(AnnouncementDetailsResponse.SupplySchedule.builder()
                    .target(AnnouncementDetailsResponse.SupplySchedule.Target.innerBuilder()
                            .label(LHAnnouncementDetailResponse.SupplySchedule.existTargetOf(houseSupplyTypeCode)
                                    ? lhDetailSupplyScheduleLabel.getTarget()
                                    : lhDetailSupplyScheduleLabel.getHouseComplexName()
                            )
                            .value(LHAnnouncementDetailResponse.SupplySchedule.existTargetOf(houseSupplyTypeCode)
                                    ? lhDetailSupplyScheduleValue.getTarget()
                                    : houseComplexName
                            )
                            .build()
                    )
                    .applicationDatetime(AnnouncementDetailsResponse.SupplySchedule.ApplicationDatetime.innerBuilder()
                            .label(lhDetailSupplyScheduleLabel.getApplicationDatetime())
                            .value(lhDetailSupplyScheduleValue.getApplicationDatetime())
                            .build()
                    )
                    .applicationMethod(AnnouncementDetailsResponse.SupplySchedule.ApplicationMethod.innerBuilder()
                            .label(lhDetailSupplyScheduleLabel.getApplicationMethod())
                            .value(lhDetailSupplyScheduleValue.getApplicationMethod())
                            .build()
                    )
                    .winnerAnnouncementDate(AnnouncementDetailsResponse.SupplySchedule.WinnerAnnouncementDate.innerBuilder()
                            .label(lhDetailSupplyScheduleLabel.getWinnerAnnouncementDate())
                            .value(lhDetailSupplyScheduleValue.getWinnerAnnouncementDate())
                            .build()
                    )
                    .paperSubmitOpenAnnouncementDate(AnnouncementDetailsResponse.SupplySchedule.PaperSubmitOpenAnnouncementDate.innerBuilder()
                            .label(lhDetailSupplyScheduleLabel.getPaperSubmitOpenAnnouncementDate())
                            .value(lhDetailSupplyScheduleValue.getPaperSubmitOpenAnnouncementDate())
                            .build()
                    )
                    .paperSubmitTerm(AnnouncementDetailsResponse.SupplySchedule.PaperSubmitTerm.innerBuilder()
                            .label("서류접수기간")
                            .value(makeTermValueFrom(lhDetailSupplyScheduleValue.getWinnerPaperSubmitStartDate(), lhDetailSupplyScheduleValue.getWinnerPaperSubmitEndDate()))
                            .build()
                    )
                    .contractTerm(AnnouncementDetailsResponse.SupplySchedule.ContractTerm.innerBuilder()
                            .label("계약체결기간")
                            .value(makeTermValueFrom(lhDetailSupplyScheduleValue.getContractStartDate(), lhDetailSupplyScheduleValue.getContractEndDate()))
                            .build()
                    )
                    .applicationTerm(AnnouncementDetailsResponse.SupplySchedule.ApplicationTerm.innerBuilder()
                            .label("접수기간")
                            .value(makeTermValueFrom(lhDetailSupplyScheduleValue.getApplicationStartDate(), lhDetailSupplyScheduleValue.getApplicationEndDate()))
                            .build()
                    )
                    .houseBrowseTerm(AnnouncementDetailsResponse.SupplySchedule.HouseBrowseTerm.innerBuilder()
                            .label("주택열람기간")
                            .value(makeTermValueFrom(lhDetailSupplyScheduleValue.getHouseBrowseStartDate(), lhDetailSupplyScheduleValue.getHouseBrowseEndDate()))
                            .build())
                    .supplyScheduleGuide(AnnouncementDetailsResponse.SupplySchedule.SupplyScheduleGuide.innerBuilder()
                            .label(lhDetailSupplyScheduleLabel.getSupplyScheduleGuide())
                            .value(lhDetailSupplyScheduleValue.getSupplyScheduleGuide())
                            .build()
                    )
                    .build()
            );
        }

        return supplySchedules;
    }

    private AnnouncementDetailsResponse.Etc buildEtcFrom(LHAnnouncementDetailResponse lhDetail) {
        LHAnnouncementDetailResponse.Etc etc = lhDetail.getEtcInfo();

        return AnnouncementDetailsResponse.Etc.builder()
                .description(AnnouncementDetailsResponse.Etc.Description.innerBuilder()
                        .label(etc.getLabel().getDescription())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getDescription()
                        )
                        .build()
                )
                .remark1(AnnouncementDetailsResponse.Etc.Remark1.innerBuilder()
                        .label(etc.getLabel().getRemark1())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getRemark1()
                        )
                        .build()
                )
                .remark2(AnnouncementDetailsResponse.Etc.Remark2.innerBuilder()
                        .label(etc.getLabel().getRemark2())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getRemark2()
                        )
                        .build()
                )
                .correctOrCancelReason(AnnouncementDetailsResponse.Etc.CorrectOrCancelReason.innerBuilder()
                        .label(etc.getLabel().getCorrectOrCancelReason())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getCorrectOrCancelReason()
                        )
                        .build()
                )
                .targetArea(AnnouncementDetailsResponse.Etc.TargetArea.innerBuilder()
                        .label(etc.getLabel().getTargetArea())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getTargetArea()
                        )
                        .build()
                )
                .targetHouse(AnnouncementDetailsResponse.Etc.TargetHouse.innerBuilder()
                        .label(etc.getLabel().getTargetHouse())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getTargetHouse()
                        )
                        .build()
                )
                .leaseTerms(AnnouncementDetailsResponse.Etc.LeaseTerms.innerBuilder()
                        .label(etc.getLabel().getLeaseTerms())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getLeaseTerms()
                        )
                        .build()
                )
                .leaseCondition(AnnouncementDetailsResponse.Etc.LeaseCondition.innerBuilder()
                        .label(etc.getLabel().getLeaseCondition())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getLeaseCondition()
                        )
                        .build()
                )
                .caution(AnnouncementDetailsResponse.Etc.Caution.innerBuilder()
                        .label(etc.getLabel().getCaution())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getCaution()
                        )
                        .build()
                )
                .supportLimitAmount(AnnouncementDetailsResponse.Etc.SupportLimitAmount.innerBuilder()
                        .label(etc.getLabel().getSupportLimitAmount())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getSupportLimitAmount()
                        )
                        .build()
                )
                .numberOfSupplyHousehold(AnnouncementDetailsResponse.Etc.NumberOfSupplyHousehold.innerBuilder()
                        .label(etc.getLabel().getNumberOfSupplyHousehold())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getNumberOfSupplyHousehold()
                        )
                        .build()
                )
                .receptionAddress(AnnouncementDetailsResponse.Etc.ReceptionAddress.innerBuilder()
                        .label(etc.getLabel().getReceptionAddress())
                        .value(etc.getValues().stream()
                                .findFirst()
                                .orElse(new LHAnnouncementDetailResponse.Etc.Value())
                                .getReceptionAddress()
                        )
                        .build()
                )
                .build();
    }

    private AnnouncementDetailsResponse.Reception buildReceptionFrom(LHAnnouncementDetailResponse lhDetail) {
        LHAnnouncementDetailResponse.Reception reception = lhDetail.getReception();
        LHAnnouncementDetailResponse.Reception.Value receptionValue = reception.getValues().stream()
                .findFirst()
                .orElse(new LHAnnouncementDetailResponse.Reception.Value());

        return AnnouncementDetailsResponse.Reception.builder()
                .address(AnnouncementDetailsResponse.Reception.Address.innerBuilder()
                        .label(reception.getLabel().getAddress())
                        .value(receptionValue.getAddress())
                        .build()
                )
                .telephoneNumber(AnnouncementDetailsResponse.Reception.TelephoneNumber.innerBuilder()
                        .label(reception.getLabel().getTelephoneNumber())
                        .value(receptionValue.getTelephoneNumber())
                        .build()
                )
                .operationTerm(AnnouncementDetailsResponse.Reception.OperationTerm.innerBuilder()
                        .label(reception.getLabel().getOperationTerm())
                        .value(makeTermValueFrom(receptionValue.getOpenDate(), receptionValue.getCloseDate()))
                        .build()
                )
                .scheduleGuide(AnnouncementDetailsResponse.Reception.ScheduleGuide.innerBuilder()
                        .label(reception.getLabel().getScheduleGuide())
                        .value(receptionValue.getScheduleGuide())
                        .build()
                )
                .receptionGuide(AnnouncementDetailsResponse.Reception.ReceptionGuide.innerBuilder()
                        .label(reception.getLabel().getReceptionGuide())
                        .value(receptionValue.getReceptionGuide())
                        .build()
                )
                .build();
    }

    private List<AnnouncementDetailsResponse.HouseComplex> buildHouseComplexesFrom(LHAnnouncementDetailResponse lhDetail, LHAnnouncementSupplyInfoResponse lhSupplyInfo) {
        List<AnnouncementDetailsResponse.HouseComplex> houseComplexes = new ArrayList<>();

        for (LHAnnouncementDetailResponse.HouseComplex.Value lhDetailHouseComplexValue : lhDetail.getHouseComplex().getValues()) {
            AnnouncementDetailsResponse.HouseComplex houseComplex = AnnouncementDetailsResponse.HouseComplex.buildFrom(lhDetail.getHouseComplex().getLabel(), lhDetailHouseComplexValue);

            List<AnnouncementDetailsResponse.HouseType> houseTypes = buildHouseTypesFrom(lhSupplyInfo, lhDetailHouseComplexValue.getHouseComplexName());
            List<AnnouncementDetailsResponse.Attachment> attachments = buildAttachmentsFrom(lhDetail.getHouseComplexAttachment(), lhDetailHouseComplexValue.getHouseComplexName());

            houseComplex.setHouseTypes(houseTypes);
            houseComplex.setAttachments(attachments);

            houseComplexes.add(houseComplex);
        }

        return houseComplexes;
    }

    private List<AnnouncementDetailsResponse.Attachment> buildAttachmentsFrom(LHAnnouncementDetailResponse.HouseComplexAttachment lhDetailHouseComplexAttachment, String houseComplexName) {
        return lhDetailHouseComplexAttachment.getValues().stream()
                .filter(lhDetailHouseComplexAttachmentValue -> isEqualsIgnoringWhitespaces(lhDetailHouseComplexAttachmentValue.getHouseComplexName(), houseComplexName))
                .map(lhDetailHouseComplexAttachmentValue -> AnnouncementDetailsResponse.Attachment.buildFrom(lhDetailHouseComplexAttachment.getLabel(), lhDetailHouseComplexAttachmentValue))
                .toList();
    }

    private List<AnnouncementDetailsResponse.HouseType> buildHouseTypesFrom(LHAnnouncementSupplyInfoResponse lhSupplyInfo, String houseComplexName) {
        LHAnnouncementSupplyInfoResponse.Label lhSupplyInfoLabel = lhSupplyInfo.getLabel();

        return lhSupplyInfo.getValues().stream()
                .filter(lhSupplyInfoValue -> isEqualsIgnoringWhitespaces(lhSupplyInfoValue.getHouseComplexName(), houseComplexName))
                .map(lhSupplyInfoValue -> AnnouncementDetailsResponse.HouseType.buildFrom(lhSupplyInfoLabel, lhSupplyInfoValue))
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
