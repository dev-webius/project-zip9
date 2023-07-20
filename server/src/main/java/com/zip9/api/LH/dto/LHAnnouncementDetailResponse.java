package com.zip9.api.LH.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zip9.api.LH.enums.HouseSupplyType;
import com.zip9.api.common.exception.GeneralException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LHAnnouncementDetailResponse {
    @JsonUnwrapped
    @Builder.Default
    private Reception reception = new Reception();
    @JsonUnwrapped
    @Builder.Default
    private HouseComplex houseComplex = new HouseComplex();
    @JsonUnwrapped
    @Builder.Default
    private HouseComplexAttachment houseComplexAttachment = new HouseComplexAttachment();
    @JsonUnwrapped
    @Builder.Default
    private SupplySchedule supplySchedule = new SupplySchedule();
    @JsonUnwrapped
    @Builder.Default
    private Attachment attachment = new Attachment();
    @JsonUnwrapped
    @Builder.Default
    private Etc etcInfo = new Etc();
    @JsonUnwrapped
    @Builder.Default
    private Qualification qualifications = new Qualification();

    @Getter
    public static class Reception {
        @JsonProperty("dsCtrtPlcNm")
        private List<Label> labels = new ArrayList<>();
        @JsonProperty("dsCtrtPlc")
        private List<Value> values = new ArrayList<>();

        @Getter
        public static class Label {
            @JsonProperty("CTRT_PLC_ADR")
            private String address = "";
            @JsonProperty("CTRT_PLC_DTL_ADR")
            private String detailAddress = "";
            @JsonProperty("SIL_OFC_TLNO")
            private String telephoneNumber = "";
            @JsonProperty("SIL_OFC_OPEN_DT")
            @JsonAlias("TSK_ST_DTTM")
            private String openDate = "";
            @JsonProperty("SIL_OFC_BCLS_DT")
            @JsonAlias("TSK_ED_DTTM")
            private String closeDate = "";
            @JsonProperty("SIL_OFC_DT")
            private String operationTerm = "";
            @JsonProperty("TSK_SCD_CTS")
            private String scheduleGuide = "";
            @JsonProperty("SIL_OFC_GUD_FCTS")
            private String receptionGuide = "";
        }

        @Getter
        public static class Value {
            @JsonProperty("CTRT_PLC_ADR")
            private String address = "";
            @JsonProperty("CTRT_PLC_DTL_ADR")
            private String detailAddress = "";
            @JsonProperty("SIL_OFC_TLNO")
            private String telephoneNumber = "";
            @JsonProperty("SIL_OFC_OPEN_DT")
            @JsonAlias("TSK_ST_DTTM")
            private String openDate = "";
            @JsonProperty("SIL_OFC_BCLS_DT")
            @JsonAlias("TSK_ED_DTTM")
            private String closeDate = "";
            @JsonProperty("SIL_OFC_DT")
            private String operationTerm = "";
            @JsonProperty("TSK_SCD_CTS")
            private String scheduleGuide = "";
            @JsonProperty("SIL_OFC_GUD_FCTS")
            private String receptionGuide = "";
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HouseComplex {
        @JsonProperty("dsSbdNm")
        @Builder.Default
        private List<Label> labels = new ArrayList<>();
        @JsonProperty("dsSbd")
        @Builder.Default
        private List<Value> values = new ArrayList<>();

//        @JsonSetter("dsSbdNm")
//        public void setLabel(List<Label> labels) {
//            if (!labels.isEmpty()) {
//                this.label = labels.get(0);
//            }
//        }

        @Getter
        public static class Label {
            @JsonProperty("BZDT_NM")
            @JsonAlias("LCC_NT_NM")
            private String houseComplexName = "";
            @JsonProperty("LCT_ARA_ADR")
            @JsonAlias("LGDN_ADR")
            private String houseComplexAddress = "";
            @JsonProperty("LCT_ARA_DTL_ADR")
            @JsonAlias("LGDN_DTL_ADR")
            private String houseComplexDetailAddress = "";
            @JsonProperty("MIN_MAX_RSDN_DDO_AR")
            @JsonAlias("DDO_AR")
            private String netLeasableArea = "";
            @JsonProperty("SUM_TOT_HSH_CNT")
            @JsonAlias("HSH_CNT")
            private String totalHouseholdCount = "";
            @JsonProperty("HTN_FMLA_DS_CD_NM")
            @JsonAlias("HTN_FMLA_DESC")
            private String heatingTypeName = "";
            @JsonProperty("MVIN_XPC_YM")
            private String expectedMoveInDate = "";
            @JsonProperty("TFFC_FCL_CTS")
            private String trafficFacilities = "";
            @JsonProperty("EDC_FCL_CTS")
            private String educationFacilities = "";
            @JsonProperty("CVN_FCL_CTS")
            private String convenientFacilities = "";
            @JsonProperty("IDT_FCL_CTS")
            private String appurtenantFacilities = "";
            @JsonProperty("SPL_INF_GUD_FCTS")
            private String supplyInfoGuide = "";
        }

        @Getter
        public static class Value {
            @JsonProperty("BZDT_NM")
            @JsonAlias("LCC_NT_NM")
            private String houseComplexName = "";
            @JsonProperty("LCT_ARA_ADR")
            @JsonAlias("LGDN_ADR")
            private String houseComplexAddress = "";
            @JsonProperty("LCT_ARA_DTL_ADR")
            @JsonAlias("LGDN_DTL_ADR")
            private String houseComplexDetailAddress = "";
            @JsonProperty("MIN_MAX_RSDN_DDO_AR")
            @JsonAlias("DDO_AR")
            private String netLeasableArea = "";
            @JsonProperty("SUM_TOT_HSH_CNT")
            @JsonAlias("HSH_CNT")
            private String totalHouseholdCount = "";
            @JsonProperty("HTN_FMLA_DS_CD_NM")
            @JsonAlias("HTN_FMLA_DESC")
            private String heatingTypeName = "";
            @JsonProperty("MVIN_XPC_YM")
            private String expectedMoveInDate = "";
            @JsonProperty("TFFC_FCL_CTS")
            private String trafficFacilities = "";
            @JsonProperty("EDC_FCL_CTS")
            private String educationFacilities = "";
            @JsonProperty("CVN_FCL_CTS")
            private String convenientFacilities = "";
            @JsonProperty("IDT_FCL_CTS")
            private String appurtenantFacilities = "";
            @JsonProperty("SPL_INF_GUD_FCTS")
            private String supplyInfoGuide = "";
        }

    }

    @Getter
    public static class HouseComplexAttachment {
        @JsonProperty("dsSbdAhflNm")
        private List<Label> labels = new ArrayList<>();
        @JsonProperty("dsSbdAhfl")
        private List<Value> values = new ArrayList<>();

        @Getter
        public static class Label {
            @JsonProperty("BZDT_NM")
            @JsonAlias("LCC_NT_NM")
            private String houseComplexName = "";
            @JsonProperty("SL_PAN_AHFL_DS_CD_NM")
            @JsonAlias("LS_SPL_INF_UPL_FL_DS_CD_NM")
            private String fileTypeName = "";
            @JsonProperty("CMN_AHFL_NM")
            private String fileName = "";
            @JsonProperty("AHFL_URL")
            private String downloadUrl = "";
        }

        @Getter
        public static class Value {
            @JsonProperty("BZDT_NM")
            @JsonAlias("LCC_NT_NM")
            private String houseComplexName = "";
            @JsonProperty("SL_PAN_AHFL_DS_CD_NM")
            @JsonAlias("LS_SPL_INF_UPL_FL_DS_CD_NM")
            private String fileTypeName = "";
            @JsonProperty("CMN_AHFL_NM")
            private String fileName = "";
            @JsonProperty("AHFL_URL")
            private String downloadUrl = "";
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupplySchedule {
        @JsonProperty("dsSplScdlNm")
        @Builder.Default
        private List<Label> labels = new ArrayList<>();
        @JsonProperty("dsSplScdl")
        @Builder.Default
        private List<Value> values = new ArrayList<>();

        @Getter
        public static class Label {
            @JsonProperty("HS_SBSC_ACP_TRG_CD_NM")
            private String target = "";
            @JsonProperty("ACP_DTTM")
            private String applicationDatetime = "";
            @JsonProperty("RMK")
            private String applicationMethod = "";
            @JsonProperty("PZWR_ANC_DT")
            private String winnerAnnouncementDate = "";
            @JsonProperty("PPR_SBM_OPE_ANC_DT")
            private String paperSubmitOpenAnnouncementDate;
            @JsonProperty("PZWR_PPR_SBM_ST_DT")
            @JsonAlias("PPR_ACP_ST_DT")
            private String winnerPaperSubmitStartDate = "";
            @JsonProperty("PZWR_PPR_SBM_ED_DT")
            @JsonAlias("PPR_ACP_CLSG_DT")
            private String winnerPaperSubmitEndDate = "";
            @JsonProperty("CTRT_ST_DT")
            private String contractStartDate = "";
            @JsonProperty("CTRT_ED_DT")
            private String contractEndDate = "";
            @JsonProperty("SPL_SCD_GUD_FCTS")
            private String supplyScheduleGuide = "";

            @JsonProperty("SBD_LGO_NM")
            private String houseComplexName = "";
            @JsonProperty("SBSC_ACP_ST_DT")
            @JsonAlias({"UST_ACP_ST_DTTM", "ACP_ST_DT"})
            private String applicationStartDate = "";
            @JsonProperty("SBSC_ACP_CLSG_DT")
            @JsonAlias({"UST_ACP_CLSG_DTTM", "ACP_ED_DT"})
            private String applicationEndDate = "";
            @JsonProperty("HS_VIE_ST_DT")
            private String houseBrowseStartDate = "";
            @JsonProperty("HS_VIE_ED_DT")
            private String houseBrowseEndDate = "";
        }

        @Getter
        public static class Value {
            @JsonProperty("HS_SBSC_ACP_TRG_CD_NM")
            private String target = "";
            @JsonProperty("ACP_DTTM")
            private String applicationDatetime = "";
            @JsonProperty("RMK")
            private String applicationMethod = "";
            @JsonProperty("PZWR_ANC_DT")
            @JsonAlias("PPR_SBM_OPE_ANC_DT")
            private String winnerAnnouncementDate = "";
            @JsonProperty("PPR_SBM_OPE_ANC_DT")
            private String paperSubmitOpenAnnouncementDate;
            @JsonProperty("PZWR_PPR_SBM_ST_DT")
            @JsonAlias("PPR_ACP_ST_DT")
            private String winnerPaperSubmitStartDate = "";
            @JsonProperty("PZWR_PPR_SBM_ED_DT")
            @JsonAlias("PPR_ACP_CLSG_DT")
            private String winnerPaperSubmitEndDate = "";
            @JsonProperty("CTRT_ST_DT")
            private String contractStartDate = "";
            @JsonProperty("CTRT_ED_DT")
            private String contractEndDate = "";
            @JsonProperty("SPL_SCD_GUD_FCTS")
            private String supplyScheduleGuide = "";

            @JsonProperty("SBD_LGO_NM")
            private String houseComplexName = "";
            @JsonProperty("SBSC_ACP_ST_DT")
            @JsonAlias({"UST_ACP_ST_DTTM", "ACP_ST_DT"})
            private String applicationStartDate = "";
            @JsonProperty("SBSC_ACP_CLSG_DT")
            @JsonAlias({"UST_ACP_CLSG_DTTM", "ACP_ED_DT"})
            private String applicationEndDate = "";
            @JsonProperty("HS_VIE_ST_DT")
            private String houseBrowseStartDate = "";
            @JsonProperty("HS_VIE_ED_DT")
            private String houseBrowseEndDate = "";
        }

        public static Boolean existTargetOf(String houseSupplyTypeCode) {
            return Stream.of(HouseSupplyType.PRE_SALE_HOUSE, HouseSupplyType.PUBLIC_RENTAL_FOR_5_OR_10_YEARS, HouseSupplyType.HOME_BASED_CHILDCARE_CENTER, HouseSupplyType.NEWLYWEDS)
                    .anyMatch(type -> type.code.equals(houseSupplyTypeCode));
        }
    }

    @Getter
    public static class Attachment {
        @JsonProperty("dsAhflInfoNm")
        private List<Label> labels = new ArrayList<>();
        @JsonProperty("dsAhflInfo")
        private List<Value> values = new ArrayList<>();

        @Getter
        public static class Label {
            @JsonProperty("SL_PAN_AHFL_DS_CD_NM")
            private String fileTypeName = "";
            @JsonProperty("CMN_AHFL_NM")
            private String fileName = "";
            @JsonProperty("AHFL_URL")
            private String downloadUrl = "";
        }

        @Getter
        public static class Value {
            @JsonProperty("SL_PAN_AHFL_DS_CD_NM")
            private String fileTypeName = "";
            @JsonProperty("CMN_AHFL_NM")
            private String fileName = "";
            @JsonProperty("AHFL_URL")
            private String downloadUrl = "";
        }
    }

    @Getter
    public static class Etc {
        @JsonProperty("dsEtcInfoNm")
        private List<Label> labels = new ArrayList<>();
        @JsonProperty("dsEtcInfo")
        private List<Value> values = new ArrayList<>();

        @Getter
        public static class Label {
            @JsonProperty("PAN_DTL_CTS")
            private String announcementDescription;

            @JsonProperty("ETC_FCTS")
            @JsonAlias({"ETC_CTS", "ETC_CTS2"})
            private String comment;

            @JsonProperty("ETC_CTS3")
            private String groupHomeAgency;

            @JsonProperty("CRC_RSN")
            private String correctOrCancelReason;

            @JsonProperty("ARAG_RCR_HSH_CTS")
            @JsonAlias("EPZ_TRG_ARA")
            private String targetArea;

            @JsonProperty("TRG_HS_CTS")
            private String targetHouse;

            @JsonProperty("LSTR_CTS")
            private String leaseTerms;

            @JsonProperty("LSC_CTS")
            private String leaseCondition;

            @JsonProperty("CAU_FCTS")
            private String caution;

            @JsonProperty("SPPT_LMT_AMT_CTS")
            private String supportLimitAmount;

            @JsonProperty("SPL_HO_CNT_CTS")
            private String numberOfSupplyHousehold;

            @JsonProperty("RQS_PLC_CTS")
            private String receptionAddress;
        }

        @Getter
        public static class Value {
            @JsonProperty("PAN_DTL_CTS")
            @JsonAlias("CRC_RSN")
            private String announcementDescription = "";

            @JsonProperty("ETC_FCTS")
            @JsonAlias({"ETC_CTS", "ETC_CTS2"})
            private String comment = "";

            @JsonProperty("ETC_CTS3")
            private String groupHomeAgency = "";

            @JsonProperty("CRC_RSN")
            private String correctOrCancelReason = "";

            @JsonProperty("ARAG_RCR_HSH_CTS")
            @JsonAlias("EPZ_TRG_ARA")
            private String targetArea = "";

            @JsonProperty("TRG_HS_CTS")
            private String targetHouse = "";

            @JsonProperty("LSTR_CTS")
            private String leaseTerms = "";

            @JsonProperty("LSC_CTS")
            private String leaseCondition = "";

            @JsonProperty("CAU_FCTS")
            private String caution = "";

            @JsonProperty("SPPT_LMT_AMT_CTS")
            private String supportLimitAmount = "";

            @JsonProperty("SPL_HO_CNT_CTS")
            private String numberOfSupplyHousehold = "";

            @JsonProperty("RQS_PLC_CTS")
            private String receptionAddress = "";

        }
    }

    @Getter
    public static class Qualification {
        @JsonProperty("dsEtcListNm")
        private List<Label> labels = new ArrayList<>();
        @JsonProperty("dsEtcList")
        private List<Value> values = new ArrayList<>();

        @Getter
        public static class Label {
            @JsonProperty("PAN_ETC_INF_CD_NM")
            private String qualificationTypeName;
            @JsonProperty("ETC_CTS")
            private String requirement;
        }

        @Getter
        public static class Value {
            @JsonProperty("PAN_ETC_INF_CD_NM")
            private String qualificationTypeName;
            @JsonProperty("ETC_CTS")
            private String requirement;
        }
    }

    public static LHAnnouncementDetailResponse jsonToObject(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(json, LHAnnouncementDetailResponse.class);
        } catch (JsonProcessingException e) {
            throw new GeneralException(e);
        }
    }
}
