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
     *
     */
    public AnnouncementResponse searchAnnouncements(AnnouncementRequest request) {
        List<LHAnnouncementRequest> lhRequests = buildLHAnnouncementRequestsFrom(request);
        List<LHAnnouncementResponse> lhAnnouncements = lhRequests.stream()
                .map(lhRequest -> lhService.searchAnnouncements(lhRequest))
                .flatMap(List::stream)
                .toList()
                .stream()
                .sorted(Comparator.comparing(LHAnnouncementResponse::getRegistDate).reversed())
                .toList()
                ;

//        lhAnnouncements.forEach(lhAnnouncement -> {
//            lhAnnouncement.setDetail(lhService.getAnnouncementDetail(buildAnnouncementDetailRequest(lhAnnouncement)));
//            lhAnnouncement.setSupplyInfo(lhService.getAnnouncementSupplyInfo(buildAnnouncementSupplyInfoRequest(lhAnnouncement)));
//        });


        List<Announcement> announcements = lhAnnouncements.stream()
                .map(this::buildAnnouncementItemFrom)
                .toList();

        AnnouncementResponse response = new AnnouncementResponse();
        AnnouncementResponse.Meta meta = response.getMeta();
        LinkedHashMap<String, List<Announcement>> announcementsByCity = response.getAnnouncements();

        for (Announcement announcement : announcements) {
            Map<String, Integer> numberOfAnnouncementsByCity = meta.getNumberOfAnnouncementsByCity();

            String cityShortName = "";

            if (numberOfAnnouncementsByCity.containsKey(announcement.getCityShortName())) {
                cityShortName = announcement.getCityShortName();
            } else {
                cityShortName = City.ETC.shortName;
            }

            announcementsByCity.get(cityShortName).add(announcement);

            numberOfAnnouncementsByCity.put(
                    cityShortName, numberOfAnnouncementsByCity.get(cityShortName) + 1
            );

        }

        return AnnouncementResponse.builder()
                .announcements(announcementsByCity)
                .meta(meta)
                .build();
    }

    private List<LHAnnouncementRequest> buildLHAnnouncementRequestsFrom(AnnouncementRequest request) {
        List<LHAnnouncementRequest> requests = new ArrayList<>();
        for (String announcementType : request.getAnnouncementTypes()) {
            for (String announcementStatus : request.getAnnouncementStatus()) {
                requests.add(LHAnnouncementRequest.builder()
                        .registStartDate(request.getRegistStartDate())
                        .registEndDate(request.getRegistEndDate())
                        .title(request.getTitle())
                        .announcementType(announcementType)
                        .announcementStatus(announcementStatus)
                        .city(request.getCity())
                        .closeStartDate(request.getCloseStartDate())
                        .closeEndDate(request.getCloseEndDate())
                        .build()
                );
            }
        }
        return requests;
    }

    /**
     * 공고 데이터 Build
     * - LH 공고 데이터 -> ZIP9 공고
     *
     */
    private Announcement buildAnnouncementItemFrom(LHAnnouncementResponse lhAnnouncement) {
        List<AnnouncementDetailsResponse> details = buildAnnouncementDetailsFrom(lhAnnouncement.getDetail(), lhAnnouncement.getSupplyInfo(), lhAnnouncement.getSupplyTypeCode());
        List<AnnouncementResponse.Position> positions = buildPositionOfHouseComplexes(details);

        Announcement.AnnouncementBuilder builder = Announcement.builder()
                .id(lhAnnouncement.getId())
                .title(lhAnnouncement.getTitle())
                .statusName(lhAnnouncement.getAnnouncementStatusName())
                .announcementTypeName(lhAnnouncement.getAnnouncementTypeName())
                .announcementDetailTypeName(lhAnnouncement.getAnnouncementDetailTypeName())
                .cityName(lhAnnouncement.getCityName())
                .detailUrlMobile(lhAnnouncement.getDetailUrlMobile())
                .detailUrl(lhAnnouncement.getDetailUrl())
                .registDate(lhAnnouncement.getRegistDate())
                .closeDate(lhAnnouncement.getCloseDate())
                .positions(positions)
                .details(details);


        if (!ObjectUtils.isEmpty(City.nameOf(lhAnnouncement.getCityName()))) {
            builder.cityShortName(City.nameOf(lhAnnouncement.getCityName()).shortName);
        }

        return builder.build();
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
                            .houseComplexName(houseComplex.getName().getValue())
                            .address(address.getRoadAddress())
                            .x(address.getX())
                            .y(address.getY())
                            .build()
                    );
                }
            }
        }
        return positions;
    }

    /**
     * 공고상세 데이터 Build
     * - (LH 공고상세정보 + LH 주택공급정보) -> ZIP9 공고상세
     *
     */
    private List<AnnouncementDetailsResponse> buildAnnouncementDetailsFrom(LHAnnouncementDetailResponse lhDetail, LHAnnouncementSupplyInfoResponse lhSupplyInfo, String houseSupplyTypeCode) {
        List<AnnouncementDetailsResponse> items = new ArrayList<>();

        Iterator<LHAnnouncementSupplyInfoResponse.Label> labelIter = lhSupplyInfo.getLabels().iterator();
        Iterator<List<LHAnnouncementSupplyInfoResponse.Value>> valueIter = lhSupplyInfo.getValues().iterator();

        while (labelIter.hasNext() && valueIter.hasNext()) {
            LHAnnouncementSupplyInfoResponse.Label supplyInfoLabel = labelIter.next();
            List<LHAnnouncementSupplyInfoResponse.Value> supplyInfoValues = valueIter.next();
            List<AnnouncementDetailsResponse.HouseComplex> houseComplexes = buildHouseComplexesFrom(lhDetail, supplyInfoLabel, supplyInfoValues, houseSupplyTypeCode);

            List<String> houseComplexNames = houseComplexes.stream()
                    .map(houseComplex -> houseComplex.getName().getValue())
                    .toList();
            Map<String, AnnouncementDetailsResponse.HouseComplex> houseComplexesMap = houseComplexes.stream()
                    .collect(Collectors.toMap(
                                    houseComplex -> houseComplex.getName().getValue(),
                                    houseComplex -> houseComplex
                    ));


            List<AnnouncementDetailsResponse.SupplySchedule> supplySchedules = buildSupplySchedulesFrom(lhDetail, houseSupplyTypeCode);

            items.add(AnnouncementDetailsResponse.builder()
                            .supplySchedules(supplySchedules)
                            .houseComplexes(AnnouncementDetailsResponse.HouseComplexes.builder()
                                    .names(houseComplexNames)
                                    .map(houseComplexesMap)
                                    .build())
                            .reception(buildReceptionFrom(lhDetail))
                            .etc(buildEtcFrom(lhDetail))
                            .build()
            );

        }

        return items;
    }

    private List<AnnouncementDetailsResponse.SupplySchedule> buildSupplySchedulesFrom(LHAnnouncementDetailResponse lhDetail, String houseSupplyTypeCode) {
        LHAnnouncementDetailResponse.SupplySchedule.Label lhDetailSupplyScheduleLabel = lhDetail.getSupplySchedule().getLabel();

        return lhDetail.getSupplySchedule().getValues().stream()
                .map(lhDetailSupplyScheduleValue -> AnnouncementDetailsResponse.SupplySchedule.builder()
                        .target(AnnouncementDetailsResponse.SupplySchedule.Target.innerBuilder()
                                .label(LHAnnouncementDetailResponse.SupplySchedule.existTargetOf(houseSupplyTypeCode)
                                        ? lhDetailSupplyScheduleLabel.getTarget()
                                        : lhDetailSupplyScheduleLabel.getHouseComplexName()
                                )
                                .value(LHAnnouncementDetailResponse.SupplySchedule.existTargetOf(houseSupplyTypeCode)
                                        ? lhDetailSupplyScheduleValue.getTarget()
                                        : lhDetailSupplyScheduleValue.getHouseComplexName()
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
                )
                .toList();
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

    /**
     * 공고상세 데이터 - 단지 데이터 Build
     *
     */
    private List<AnnouncementDetailsResponse.HouseComplex> buildHouseComplexesFrom(LHAnnouncementDetailResponse lhDetail, LHAnnouncementSupplyInfoResponse.Label lhSupplyInfoLabel, List<LHAnnouncementSupplyInfoResponse.Value> lhSupplyInfoValues, String houseSupplyTypeCode) {
        List<AnnouncementDetailsResponse.HouseComplex> result = new ArrayList<>();

        // 단지 및 단지 하위 데이터 추출
        LHAnnouncementDetailResponse.HouseComplex lhDetailHouseComplex = lhDetail.getHouseComplex();
        LHAnnouncementDetailResponse.HouseComplexAttachment lhDetailHouseComplexAttachment = lhDetail.getHouseComplexAttachment();

        for (LHAnnouncementDetailResponse.HouseComplex.Value lhDetailhouseComplexValue : lhDetailHouseComplex.getValues()) {
            String houseComplexName = lhDetailhouseComplexValue.getHouseComplexName();

            List<LHAnnouncementDetailResponse.HouseComplexAttachment.Value> lhHouseComplexAttachmentValues = lhDetailHouseComplexAttachment.getValues().stream()
                    .filter(attachment -> isEqualsIgnoringWhitespaces(attachment.getHouseComplexName(), houseComplexName))
                    .toList();

            List<AnnouncementDetailsResponse.Attachment> attachments = buildAttachmentsFrom(lhDetailHouseComplexAttachment, houseComplexName, lhHouseComplexAttachmentValues);
            List<AnnouncementDetailsResponse.HouseType> houseTypes = buildHouseTypesFrom(lhSupplyInfoLabel, lhSupplyInfoValues, houseComplexName);

            result.add(AnnouncementDetailsResponse.HouseComplex.builder()
                    .name(AnnouncementDetailsResponse.HouseComplex.Name.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getHouseComplexName())
                            .value(lhDetailhouseComplexValue.getHouseComplexName())
                            .build()
                    )
                    .address(AnnouncementDetailsResponse.HouseComplex.Address.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getHouseComplexAddress())
                            .value(lhDetailhouseComplexValue.getHouseComplexAddress())
                            .build()
                    )
                    .detailAddress(AnnouncementDetailsResponse.HouseComplex.DetailAddress.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getHouseComplexDetailAddress())
                            .value(lhDetailhouseComplexValue.getHouseComplexDetailAddress())
                            .build()
                    )
                    .netLeasableAreaRange(AnnouncementDetailsResponse.HouseComplex.NetLeasableAreaRange.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getNetLeasableArea())
                            .value(lhDetailhouseComplexValue.getNetLeasableArea())
                            .build()
                    )
                    .totalOfHousehold(AnnouncementDetailsResponse.HouseComplex.TotalOfHousehold.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getTotalHouseholdCount())
                            .value(lhDetailhouseComplexValue.getTotalHouseholdCount())
                            .build()
                    )
                    .heatingTypeName(AnnouncementDetailsResponse.HouseComplex.HeatingTypeName.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getHeatingTypeName())
                            .value(lhDetailhouseComplexValue.getHeatingTypeName())
                            .build()
                    )
                    .expectedMoveInDate(AnnouncementDetailsResponse.HouseComplex.ExpectedMoveInDate.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getExpectedMoveInDate())
                            .value(lhDetailhouseComplexValue.getExpectedMoveInDate())
                            .build()
                    )
                    .trafficFacilities(AnnouncementDetailsResponse.HouseComplex.TrafficFacilities.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getTrafficFacilities())
                            .value(lhDetailhouseComplexValue.getTrafficFacilities())
                            .build()
                    )
                    .educationFacilities(AnnouncementDetailsResponse.HouseComplex.EducationFacilities.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getEducationFacilities())
                            .value(lhDetailhouseComplexValue.getEducationFacilities())
                            .build()
                    )
                    .convenientFacilities(AnnouncementDetailsResponse.HouseComplex.ConvenientFacilities.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getConvenientFacilities())
                            .value(lhDetailhouseComplexValue.getConvenientFacilities())
                            .build()
                    )
                    .appurtenantFacilities(AnnouncementDetailsResponse.HouseComplex.AppurtenantFacilities.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getAppurtenantFacilities())
                            .value(lhDetailhouseComplexValue.getAppurtenantFacilities())
                            .build()
                    )
                    .supplyInfoGuide(AnnouncementDetailsResponse.HouseComplex.SupplyInfoGuide.innerBuilder()
                            .label(lhDetailHouseComplex.getLabel().getSupplyInfoGuide())
                            .value(lhDetailhouseComplexValue.getSupplyInfoGuide())
                            .build()
                    )
                    .houseTypes(houseTypes)
                    .attachments(attachments)
                    .build()
            );

        }

        return result;
    }

    private List<AnnouncementDetailsResponse.Attachment> buildAttachmentsFrom(LHAnnouncementDetailResponse.HouseComplexAttachment lhDetailHouseComplexAttachment, String houseComplexName, List<LHAnnouncementDetailResponse.HouseComplexAttachment.Value> lhHouseComplexAttachmentValues) {
        return lhHouseComplexAttachmentValues.stream()
                .filter(houseComplexAttachment -> isEqualsIgnoringWhitespaces(houseComplexAttachment.getHouseComplexName(), houseComplexName))
                .map(houseComplexAttachment -> AnnouncementDetailsResponse.Attachment.builder()
                        .fileName(AnnouncementDetailsResponse.Attachment.FileName.innerBuilder()
                                .label(lhDetailHouseComplexAttachment.getLabel().getFileName())
                                .value(houseComplexAttachment.getFileName())
                                .build()
                        )
                        .fileTypeName(AnnouncementDetailsResponse.Attachment.FileTypeName.innerBuilder()
                                .label(lhDetailHouseComplexAttachment.getLabel().getFileExtends())
                                .value(houseComplexAttachment.getFileExtends())
                                .build()
                        )
                        .downloadUrl(AnnouncementDetailsResponse.Attachment.DownloadUrl.innerBuilder()
                                .label(lhDetailHouseComplexAttachment.getLabel().getDownloadUrl())
                                .value(houseComplexAttachment.getDownloadUrl())
                                .build()
                        )
                        .build()
                )
                .toList();
    }

    private List<AnnouncementDetailsResponse.HouseType> buildHouseTypesFrom(LHAnnouncementSupplyInfoResponse.Label lhSupplyInfoLabel, List<LHAnnouncementSupplyInfoResponse.Value> lhSupplyInfoValues, String houseComplexName) {
        return lhSupplyInfoValues.stream()
                .filter(lhSupplyInfoValue -> isEqualsIgnoringWhitespaces(lhSupplyInfoValue.getHouseComplexName(), houseComplexName))
                .map(lhSupplyInfoValue -> AnnouncementDetailsResponse.HouseType.builder()
                        .houseTypeName(AnnouncementDetailsResponse.HouseType.HouseTypeName.innerBuilder()
                                .label(lhSupplyInfoLabel.getHouseTypeName())
                                .value(lhSupplyInfoValue.getHouseTypeName())
                                .build()
                        )
                        .supplyArea(AnnouncementDetailsResponse.HouseType.SupplyArea.innerBuilder()
                                .label(lhSupplyInfoLabel.getSupplyArea())
                                .value(lhSupplyInfoValue.getSupplyArea())
                                .build()
                        )
                        .netLeasableArea(AnnouncementDetailsResponse.HouseType.NetLeasableArea.innerBuilder()
                                .label(lhSupplyInfoLabel.getNetLeasableArea())
                                .value(lhSupplyInfoValue.getNetLeasableArea())
                                .build()
                        )
                        .numberOfHousehold(AnnouncementDetailsResponse.HouseType.NumberOfHousehold.innerBuilder()
                                .label(lhSupplyInfoLabel.getNumberOfHousehold())
                                .value(lhSupplyInfoValue.getNumberOfHousehold())
                                .build()
                        )
                        .numberOfSupplyHousehold(AnnouncementDetailsResponse.HouseType.NumberOfSupplyHousehold.innerBuilder()
                                .label(lhSupplyInfoLabel.getNumberOfSupplyHousehold())
                                .value(lhSupplyInfoValue.getNumberOfSupplyHousehold())
                                .build()
                        )
                        .numberOfApplicants(AnnouncementDetailsResponse.HouseType.NumberOfApplicants.innerBuilder()
                                .label(lhSupplyInfoLabel.getNumberOfApplicants())
                                .value(lhSupplyInfoValue.getNumberOfApplicants())
                                .build()
                        )
                        .numberOfCandidates(AnnouncementDetailsResponse.HouseType.NumberOfCandidates.innerBuilder()
                                .label(lhSupplyInfoLabel.getNumberOfCandidates())
                                .value(lhSupplyInfoValue.getNumberOfCandidates())
                                .build()
                        )
                        .amount(AnnouncementDetailsResponse.HouseType.Amount.innerBuilder()
                                .label(lhSupplyInfoLabel.getAmount())
                                .value(lhSupplyInfoValue.getAmount())
                                .build()
                        )
                        .rentFee(AnnouncementDetailsResponse.HouseType.RentFee.innerBuilder()
                                .label(lhSupplyInfoLabel.getRentFee())
                                .value(lhSupplyInfoValue.getRentFee())
                                .build()
                        )
                        .rentFeeEtc(AnnouncementDetailsResponse.HouseType.RentFeeEtc.innerBuilder()
                                .label(lhSupplyInfoLabel.getRentFeeEtc())
                                .value(lhSupplyInfoValue.getRentFeeEtc())
                                .build()
                        )
                        .build()
                )
                .toList();
    }


    private AnnouncementDetailRequest buildAnnouncementDetailRequest(LHAnnouncementResponse announcement) {
        return AnnouncementDetailRequest.builder()
                .announcementId(announcement.getId())
                .announcementTypeCode(announcement.getAnnouncementTypeCode())
                .announcementDetailTypeCode(announcement.getAnnouncementDetailTypeCode())
                .crmCode(announcement.getCrmCode())
                .supplyTypeCode(announcement.getSupplyTypeCode())
                .build();
    }

    private AnnouncementSupplyInfoRequest buildAnnouncementSupplyInfoRequest(LHAnnouncementResponse announcement) {
        return AnnouncementSupplyInfoRequest.builder()
                .announcementId(announcement.getId())
                .announcementTypeCode(announcement.getAnnouncementTypeCode())
                .announcementDetailTypeCode(announcement.getAnnouncementDetailTypeCode())
                .crmCode(announcement.getCrmCode())
                .supplyTypeCode(announcement.getSupplyTypeCode())
                .build();
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
