package com.zip9.api.announcement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zip9.api.LH.dto.LHAnnouncementDetailResponse;
import com.zip9.api.LH.dto.LHAnnouncementSupplyInfoResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDetailsResponse {
    @Schema(description = "공급일정")
    private List<SupplySchedule> supplySchedules;
    @Schema(description = "단지정보")
    private HouseComplexes houseComplexes;
    @Schema(description = "접수처 정보")
    private Reception reception;
    @Schema(description = "공고 첨부파일")
    private List<Attachment> attachments;
    @Schema(description = "기타")
    private Etc etc;

    @Getter
    @Builder
    public static class HouseComplexes {
        @ArraySchema(schema = @Schema(description = "단지명"))
        private List<String> names;
        @Schema(description = "주택단지별 단지정보")
        private Map<String, HouseComplex> map;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HouseComplex {
        @Builder.Default
        @Schema(description = "단지명")
        private String name = "";
        @Builder.Default
        @Schema(description = "주소")
        private String address = "";
        @Builder.Default
        @Schema(description = "상세주소")
        private String detailAddress = "";
        @Builder.Default
        @Schema(description = "전용면적")
        private String netLeasableAreaRange = "";
        @Builder.Default
        @Schema(description = "총세대수")
        private String totalOfHousehold = "";
        @Builder.Default
        @Schema(description = "난방방식")
        private String heatingTypeName = "";
        @Builder.Default
        @Schema(description = "입주예정월")
        private String expectedMoveInDate = "";
        @Builder.Default
        @Schema(description = "교통여건")
        private String trafficFacilities = "";
        @Builder.Default
        @Schema(description = "교육환경")
        private String educationFacilities = "";
        @Builder.Default
        @Schema(description = "편의시설")
        private String convenientFacilities = "";
        @Builder.Default
        @Schema(description = "부대시설")
        private String appurtenantFacilities = "";
        @Builder.Default
        @Schema(description = "안내사항")
        private String supplyInfoGuide = "";
        @Builder.Default
        @Schema(description = "첨부파일")
        private List<Attachment> attachments = new ArrayList<>();
        @Builder.Default
        @Schema(description = "주택유형")
        private List<HouseType> houseTypes = new ArrayList<>();

        @JsonIgnore
        public String getNameOrDetailAddress() {
            return StringUtils.hasLength(name) ? name : detailAddress;
        }

        public static HouseComplex buildFrom(LHAnnouncementDetailResponse.HouseComplex.Value lhDetailHouseComplexValue) {
            return HouseComplex.builder()
                    .name(lhDetailHouseComplexValue.getHouseComplexName())
                    .address(lhDetailHouseComplexValue.getHouseComplexAddress())
                    .detailAddress(lhDetailHouseComplexValue.getHouseComplexDetailAddress())
                    .netLeasableAreaRange(lhDetailHouseComplexValue.getNetLeasableArea())
                    .totalOfHousehold(lhDetailHouseComplexValue.getTotalHouseholdCount())
                    .heatingTypeName(lhDetailHouseComplexValue.getHeatingTypeName())
                    .expectedMoveInDate(lhDetailHouseComplexValue.getExpectedMoveInDate())
                    .trafficFacilities(lhDetailHouseComplexValue.getTrafficFacilities())
                    .educationFacilities(lhDetailHouseComplexValue.getEducationFacilities())
                    .convenientFacilities(lhDetailHouseComplexValue.getConvenientFacilities())
                    .appurtenantFacilities(lhDetailHouseComplexValue.getAppurtenantFacilities())
                    .supplyInfoGuide(lhDetailHouseComplexValue.getSupplyInfoGuide())
                    .build();
        }

        public void setAttachments(List<Attachment> attachments) {
            this.attachments = attachments;
        }

        public void setHouseTypes(List<HouseType> houseTypes) {
            this.houseTypes = houseTypes;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class HouseType {
        @Builder.Default
        @Schema(description = "주택유형명")
        private String houseTypeName = "";
        @Builder.Default
        @Schema(description = "공급면적")
        private String supplyArea = "";
        @Builder.Default
        @Schema(description = "전용면적")
        private String netLeasableArea = "";
        @Builder.Default
        @Schema(description = "세대수")
        private String numberOfHousehold = "";
        @Builder.Default
        @Schema(description = "금회공급세대수")
        private String numberOfSupplyHousehold = "";
        @Builder.Default
        @Schema(description = "모집인원")
        private String numberOfApplicants = "";
        @Builder.Default
        @Schema(description = "예비자수")
        private String numberOfCandidates = "";
        @Builder.Default
        @Schema(description = "임대보증금")
        private String amount = "";
        @Builder.Default
        @Schema(description = "월임대료")
        private String rentFee = "";
        @Builder.Default
        @Schema(description = "월임대료(기타)")
        private String rentFeeEtc = "";

        public static HouseType buildFrom(LHAnnouncementSupplyInfoResponse.Value lhHouseSupplyInfoValue) {
            return HouseType.builder()
                    .houseTypeName(lhHouseSupplyInfoValue.getHouseTypeName())
                    .supplyArea(lhHouseSupplyInfoValue.getSupplyArea())
                    .netLeasableArea(lhHouseSupplyInfoValue.getNetLeasableArea())
                    .numberOfHousehold(lhHouseSupplyInfoValue.getNumberOfHousehold())
                    .numberOfSupplyHousehold(lhHouseSupplyInfoValue.getNumberOfSupplyHousehold())
                    .numberOfApplicants(lhHouseSupplyInfoValue.getNumberOfApplicants())
                    .numberOfCandidates(lhHouseSupplyInfoValue.getNumberOfCandidates())
                    .amount(lhHouseSupplyInfoValue.getAmount())
                    .rentFee(lhHouseSupplyInfoValue.getRentFee())
                    .rentFeeEtc(lhHouseSupplyInfoValue.getRentFeeEtc())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Attachment {
        @Builder.Default
        @Schema(description = "첨부파일명")
        private String fileName = "";
        @Builder.Default
        @Schema(description = "파일구분명")
        private String fileTypeName = "";
        @Builder.Default
        @Schema(description = "다운로드 URL")
        private String downloadUrl = "";

        public static Attachment buildFrom(LHAnnouncementDetailResponse.HouseComplexAttachment.Value lhDetailHouseComplexAttachmentValue) {
            return Attachment.builder()
                    .fileName(lhDetailHouseComplexAttachmentValue.getFileName())
                    .fileTypeName(lhDetailHouseComplexAttachmentValue.getFileTypeName())
                    .downloadUrl(lhDetailHouseComplexAttachmentValue.getDownloadUrl())
                    .build();
        }

    }

    @Getter
    @Builder
    public static class SupplySchedule {
        @Builder.Default
        @Schema(description = "공급대상 (단지 또는 순위)")
        private String target = "";
        @Builder.Default
        @Schema(description = "신청일시")
        private String applicationDatetime = "";
        @Builder.Default
        @Schema(description = "신청방법")
        private String applicationMethod = "";
        @Builder.Default
        @Schema(description = "당첨자 발표일")
        private String winnerAnnouncementDate = "";
        @Builder.Default
        @Schema(description = "서류제출 대상자 발표일")
        private String paperSubmitOpenAnnouncementDate = "";
        @Builder.Default
        @Schema(description = "서류제출기간")
        private String paperSubmitTerm = "";
        @Builder.Default
        @Schema(description = "계약체결기간")
        private String contractTerm = "";
        @Builder.Default
        @Schema(description = "접수기간")
        private String applicationTerm = "";
        @Builder.Default
        @Schema(description = "주택열람기간")
        private String houseBrowseTerm = "";
        @Builder.Default
        @Schema(description = "공급일정 안내사항")
        private String supplyScheduleGuide = "";
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reception {
        @Builder.Default
        @Schema(description = "주소")
        private String address = "";
        @Builder.Default
        @Schema(description = "전화번호")
        private String telephoneNumber = "";
        @Builder.Default
        @Schema(description = "운영기간")
        private String operationTerm = "";
        @Builder.Default
        @Schema(description = "일정내용")
        private String scheduleGuide = "";
        @Builder.Default
        @Schema(description = "안내사항")
        private String receptionGuide = "";
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Etc {
        @Builder.Default
        @Schema(description = "기타사항")
        private String comment = "";
        @Builder.Default
        @Schema(description = "공고내용")
        private String announcementDescription = "";
        @Builder.Default
        @Schema(description = "정정/취소사유")
        private String correctOrCancelReason = "";
        @Builder.Default
        @Schema(description = "모집지역")
        private String targetArea = "";
        @Builder.Default
        @Schema(description = "대상주택")
        private String targetHouse = "";
        @Builder.Default
        @Schema(description = "임대기간")
        private String leaseTerms = "";
        @Builder.Default
        @Schema(description = "임대조건")
        private String leaseCondition = "";
        @Builder.Default
        @Schema(description = "주의사항")
        private String caution = "";
        @Builder.Default
        @Schema(description = "지원한도액")
        private String supportLimitAmount = "";
        @Builder.Default
        @Schema(description = "공급호수")
        private String numberOfSupplyHousehold = "";
        @Builder.Default
        @Schema(description = "주택열람 기간")
        private String receptionAddress = "";
        @Builder.Default
        @Schema(description = "공동생활가정 운영기관")
        private String groupHomeAgency = "";
    }
}
