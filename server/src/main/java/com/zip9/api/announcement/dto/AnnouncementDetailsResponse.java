package com.zip9.api.announcement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDetailsResponse {
    private List<SupplySchedule> supplySchedules;
    private HouseComplexes houseComplexes;
    private Reception reception;
    private Etc etc;

    @Getter
    @Builder
    public static class HouseComplexes {
        private List<String> names;
        private Map<String, HouseComplex> map;
    }

    @Getter
    @Builder
    public static class HouseComplex {
        @JsonProperty("nameItem")
        private Name name;
        @JsonProperty("addressItem")
        private Address address;
        @JsonProperty("detailAddressItem")
        private DetailAddress detailAddress;
        @JsonProperty("netLeasableAreaRangeItem")
        private NetLeasableAreaRange netLeasableAreaRange;
        @JsonProperty("totalOfHouseholdItem")
        private TotalOfHousehold totalOfHousehold;
        @JsonProperty("heatingTypeNameItem")
        private HeatingTypeName heatingTypeName;
        @JsonProperty("expectedMoveInDateItem")
        private ExpectedMoveInDate expectedMoveInDate;
        @JsonProperty("trafficFacilitiesItem")
        private TrafficFacilities trafficFacilities;
        @JsonProperty("educationFacilitiesItem")
        private EducationFacilities educationFacilities;
        @JsonProperty("convenientFacilitiesItem")
        private ConvenientFacilities convenientFacilities;
        @JsonProperty("appurtenantFacilitiesItem")
        private AppurtenantFacilities appurtenantFacilities;
        @JsonProperty("supplyInfoGuideItem")
        private SupplyInfoGuide supplyInfoGuide;

        private List<Attachment> attachments;
        private List<HouseType> houseTypes;

        public static class Name extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public Name(String label, String value) {
                super(label, value);
            }
        }

        public static class Address extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public Address(String label, String value) {
                super(label, value);
            }
        }

        public static class DetailAddress extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public DetailAddress(String label, String value) {
                super(label, value);
            }
        }

        public static class NetLeasableAreaRange extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public NetLeasableAreaRange(String label, String value) {
                super(label, value);
            }
        }

        public static class TotalOfHousehold extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public TotalOfHousehold(String label, String value) {
                super(label, value);
            }
        }

        public static class HeatingTypeName extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public HeatingTypeName(String label, String value) {
                super(label, value);
            }
        }

        public static class ExpectedMoveInDate extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public ExpectedMoveInDate(String label, String value) {
                super(label, value);
            }
        }

        public static class TrafficFacilities extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public TrafficFacilities(String label, String value) {
                super(label, value);
            }
        }

        public static class EducationFacilities extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public EducationFacilities(String label, String value) {
                super(label, value);
            }
        }

        public static class ConvenientFacilities extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public ConvenientFacilities(String label, String value) {
                super(label, value);
            }
        }

        public static class AppurtenantFacilities extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public AppurtenantFacilities(String label, String value) {
                super(label, value);
            }
        }

        public static class SupplyInfoGuide extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public SupplyInfoGuide(String label, String value) {
                super(label, value);
            }
        }

    }

    @Getter
    @Builder
    public static class HouseType {
        @JsonProperty("houseTypeNameItem")
        private HouseTypeName houseTypeName; // 29A
        @JsonProperty("supplyAreaItem")
        private SupplyArea supplyArea; // "45.4786"
        @JsonProperty("netLeasableAreaItem")
        private NetLeasableArea netLeasableArea; // "29.72"
        @JsonProperty("numberOfHouseholdItem")
        private NumberOfHousehold numberOfHousehold; // "116"
        @JsonProperty("numberOfSupplyHouseholdItem")
        private NumberOfSupplyHousehold numberOfSupplyHousehold; // "20"
        @JsonProperty("numberOfApplicantsItem")
        private NumberOfApplicants numberOfApplicants; // ""
        @JsonProperty("numberOfCandidatesItem")
        private NumberOfCandidates numberOfCandidates; // ""
        @JsonProperty("amountItem")
        private Amount amount; // "공고문 참조"
        @JsonProperty("rentFeeItem")
        private RentFee rentFee; // "공고문 참조"
        @JsonProperty("rentFeeEtcItem")
        private RentFeeEtc rentFeeEtc; // ""

        public static class HouseTypeName extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public HouseTypeName(String label, String value) {
                super(label, value);
            }
        }
        public static class SupplyArea extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public SupplyArea(String label, String value) {
                super(label, value);
            }
        }
        public static class NetLeasableArea extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public NetLeasableArea(String label, String value) {
                super(label, value);
            }
        }
        public static class NumberOfHousehold extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public NumberOfHousehold(String label, String value) {
                super(label, value);
            }
        }
        public static class NumberOfSupplyHousehold extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public NumberOfSupplyHousehold(String label, String value) {
                super(label, value);
            }
        }
        public static class NumberOfApplicants extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public NumberOfApplicants(String label, String value) {
                super(label, value);
            }
        }
        public static class NumberOfCandidates extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public NumberOfCandidates(String label, String value) {
                super(label, value);
            }
        }
        public static class Amount extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public Amount(String label, String value) {
                super(label, value);
            }
        }
        public static class RentFee extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public RentFee(String label, String value) {
                super(label, value);
            }
        }
        public static class RentFeeEtc extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public RentFeeEtc(String label, String value) {
                super(label, value);
            }
        }
    }

    @Getter
    @Builder
    public static class Attachment {
        @JsonProperty("fileNameItem")
        private FileName fileName;
        @JsonProperty("fileTypeNameItem")
        private FileTypeName fileTypeName;
        @JsonProperty("downloadUrlItem")
        private DownloadUrl downloadUrl;

        public static class FileName extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public FileName(String label, String value) {
                super(label, value);
            }
        }

        public static class FileTypeName extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public FileTypeName(String label, String value) {
                super(label, value);
            }
        }

        public static class DownloadUrl extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public DownloadUrl(String label, String value) {
                super(label, value);
            }
        }

    }

    @Getter
    @Builder
    public static class SupplySchedule {
        @JsonProperty("targetItem")
        private Target target;
        @JsonProperty("applicationDatetimeItem")
        private ApplicationDatetime applicationDatetime;
        @JsonProperty("applicationMethodItem")
        private ApplicationMethod applicationMethod;
        @JsonProperty("winnerAnnouncementDateItem")
        private WinnerAnnouncementDate winnerAnnouncementDate;
        @JsonProperty("paperSubmitOpenAnnouncementDateItem")
        private PaperSubmitOpenAnnouncementDate paperSubmitOpenAnnouncementDate;
        @JsonProperty("paperSubmitTermItem")
        private PaperSubmitTerm paperSubmitTerm;
        @JsonProperty("contractTermItem")
        private ContractTerm contractTerm;
        @JsonProperty("applicationTermItem")
        private ApplicationTerm applicationTerm;
        @JsonProperty("supplyScheduleGuideItem")
        private SupplyScheduleGuide supplyScheduleGuide;
        @JsonProperty("houseBrowseTermItem")
        private HouseBrowseTerm houseBrowseTerm;
        public static class Target extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public Target(String label, String value) {
                super(label, value);
            }
        }
        public static class ApplicationDatetime extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public ApplicationDatetime(String label, String value) {
                super(label, value);
            }
        }
        public static class ApplicationMethod extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public ApplicationMethod(String label, String value) {
                super(label, value);
            }
        }
        public static class WinnerAnnouncementDate extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public WinnerAnnouncementDate(String label, String value) {
                super(label, value);
            }
        }
        public static class PaperSubmitOpenAnnouncementDate extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public PaperSubmitOpenAnnouncementDate(String label, String value) {
                super(label, value);
            }
        }
        public static class PaperSubmitTerm extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public PaperSubmitTerm(String label, String value) {
                super(label, value);
            }
        }
        public static class ContractTerm extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public ContractTerm(String label, String value) {
                super(label, value);
            }
        }
        public static class ApplicationTerm extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public ApplicationTerm(String label, String value) {
                super(label, value);
            }
        }
        public static class HouseBrowseTerm extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public HouseBrowseTerm(String label, String value) {
                super(label, value);
            }
        }
        public static class SupplyScheduleGuide extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public SupplyScheduleGuide(String label, String value) {
                super(label, value);
            }
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reception {
        @JsonProperty("addressItem")
        private Address address;
        @JsonProperty("telephoneNumberItem")
        private TelephoneNumber telephoneNumber; //"1600-1004, 1670-0003"
        @JsonProperty("operationTermItem")
        private OperationTerm operationTerm; //"2023.07.21 ~ 2023.07.21"
        @JsonProperty("scheduleGuideItem")
        private ScheduleGuide scheduleGuide;
        @JsonProperty("receptionGuideItem")
        private ReceptionGuide receptionGuide;

        public static class Address extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public Address(String label, String value) {
                super(label, value);
            }
        }
        public static class TelephoneNumber extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public TelephoneNumber(String label, String value) {
                super(label, value);
            }
        }
        public static class OperationTerm extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public OperationTerm(String label, String value) {
                super(label, value);
            }
        }
        public static class ScheduleGuide extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public ScheduleGuide(String label, String value) {
                super(label, value);
            }
        }
        public static class ReceptionGuide extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public ReceptionGuide(String label, String value) {
                super(label, value);
            }
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Etc {
        @JsonProperty("descriptionItem")
        private Description description;
        @JsonProperty("remark1Item")
        private Remark1 remark1;
        @JsonProperty("remark2Item")
        private Remark2 remark2;
        @JsonProperty("correctOrCancelReasonItem")
        private CorrectOrCancelReason correctOrCancelReason; // ""
        @JsonProperty("targetAreaItem")
        private TargetArea targetArea; // "인천 남동구, 미추홀구, 부평구, 서구, 중구"
        @JsonProperty("targetHouseItem")
        private TargetHouse targetHouse; // null
        @JsonProperty("leaseTermsItem")
        private LeaseTerms leaseTerms; // "2년, 재계약 9회 가능(입주자격 유지시 최장 20년까지 거주가능)"
        @JsonProperty("leaseConditionItem")
        private LeaseCondition leaseCondition; // "시중 시세의 30~40% 수준 (자세한 내용은 상단의 모집공고문 및 주택목록, QnA 참조)"
        @JsonProperty("cautionItem")
        private Caution caution; // ""
        @JsonProperty("supportLimitAmountItem")
        private SupportLimitAmount supportLimitAmount; // null
        @JsonProperty("numberOfSupplyHouseholdItem")
        private NumberOfSupplyHousehold numberOfSupplyHousehold; // null
        @JsonProperty("receptionAddressItem")
        private ReceptionAddress receptionAddress; // null

        public static class Description extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public Description(String label, String value) {
                super(label, value);
            }
        }
        public static class Remark1 extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public Remark1(String label, String value) {
                super(label, value);
            }
        }
        public static class Remark2 extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public Remark2(String label, String value) {
                super(label, value);
            }
        }
        public static class CorrectOrCancelReason extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public CorrectOrCancelReason(String label, String value) {
                super(label, value);
            }
        }
        public static class TargetArea extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public TargetArea(String label, String value) {
                super(label, value);
            }
        }
        public static class TargetHouse extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public TargetHouse(String label, String value) {
                super(label, value);
            }
        }
        public static class LeaseTerms extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public LeaseTerms(String label, String value) {
                super(label, value);
            }
        }
        public static class LeaseCondition extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public LeaseCondition(String label, String value) {
                super(label, value);
            }
        }
        public static class Caution extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public Caution(String label, String value) {
                super(label, value);
            }
        }
        public static class SupportLimitAmount extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public SupportLimitAmount(String label, String value) {
                super(label, value);
            }
        }
        public static class NumberOfSupplyHousehold extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public NumberOfSupplyHousehold(String label, String value) {
                super(label, value);
            }
        }
        public static class ReceptionAddress extends LabelAndValue {
            @Builder(builderMethodName = "innerBuilder")
            public ReceptionAddress(String label, String value) {
                super(label, value);
            }
        }
    }




}
