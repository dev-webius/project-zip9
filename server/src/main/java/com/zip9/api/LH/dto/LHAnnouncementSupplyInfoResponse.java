package com.zip9.api.LH.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zip9.api.common.exception.GeneralException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LHAnnouncementSupplyInfoResponse {
    @JsonProperty("dsList01Nm")
    @Builder.Default
    private List<Label> labels01 = new ArrayList<>();
    @JsonProperty("dsList02Nm")
    @Builder.Default
    private List<Label> labels02 = new ArrayList<>();
    @JsonProperty("dsList03Nm")
    @Builder.Default
    private List<Label> labels03 = new ArrayList<>();
    @JsonProperty("dsList01")
    @Builder.Default
    private List<Value> values01 = new ArrayList<>();
    @JsonProperty("dsList02")
    @Builder.Default
    private List<Value> values02 = new ArrayList<>();
    @JsonProperty("dsList03")
    @Builder.Default
    private List<Value> values03 = new ArrayList<>();

    @JsonSetter("dsList01")
    public void setValues01(List<Value> values) {
        this.values01 = values.stream().filter(ObjectUtils::isNotEmpty).toList();
    }

    @JsonSetter("dsList02")
    public void setValues02(List<Value> values) {
        this.values02 = values.stream().filter(ObjectUtils::isNotEmpty).toList();
    }

    @JsonSetter("dsList03")
    public void setValues03(List<Value> values) {
        this.values03 = values.stream().filter(ObjectUtils::isNotEmpty).toList();
    }

    @JsonIgnore
    public Label getLabel() {
        if (!values01.isEmpty()) {
            return labels01.stream().findFirst().orElse(new Label());
        } else if (!values02.isEmpty()) {
            return labels02.stream().findFirst().orElse(new Label());
        } else if (!values03.isEmpty()) {
            return labels03.stream().findFirst().orElse(new Label());
        } else {
            return new Label();
        }
    }
    @JsonIgnore
    public List<Value> getValues() {
        if (!values01.isEmpty()) {
            return values01;
        } else if (!values02.isEmpty()) {
            return values02;
        } else if (!values03.isEmpty()) {
            return values03;
        } else {
            return new ArrayList<>();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SupplyInfo {
        @JsonProperty("SBD_CNP_NM")
        @JsonAlias({"CNP_NM", "SGG_NM", "ARA", "ADR"})
        @Builder.Default
        private String address = "";
        @JsonProperty("LTR_UNT_NM")
        @JsonAlias({"SBD_LGO_ADR", "DNG_HS_ADR"})
        @Builder.Default
        private String detailAddress = "";
        @JsonProperty("BZDT_NM")
        @JsonAlias({"SBD_LGO_NM", "HO_NO", "VLD_VL_NM"})
        @Builder.Default
        private String houseComplexName = "";
        @JsonProperty("HTY_NNA")
        @JsonAlias({"HTY_NM", "HTY_DS_NM", "SX_PP_DS_NM"})
        @Builder.Default
        private String houseTypeName = "";
        @JsonProperty("SPL_AR")
        @Builder.Default
        private String supplyArea = "";
        @JsonProperty("DDO_AR")
        @JsonAlias("RSDN_DDO_AR")
        @Builder.Default
        private String netLeasableArea = "";
        @JsonProperty("HSH_CNT")
        @JsonAlias({"TOT_HSH_CNT", "RQS_HSH_CNT"})
        @Builder.Default
        private String numberOfHousehold = "";
        @JsonProperty("NOW_HSH_CNT")
        @JsonAlias({"SIL_HSH_CNT", "GNR_SPL_RMNO", "LTR_SPL_RMNO", "SPL_RMNO", "TOT_RSDC_SPL_QOM", "SPL_QOM"})
        @Builder.Default
        private String numberOfSupplyHousehold = "";
        @JsonProperty("QUP_CNT")
        @JsonAlias("PZWR_CNT")
        @Builder.Default
        private String numberOfApplicants = "";
        @JsonProperty("CAL_QUP_CNT")
        @Builder.Default
        private String numberOfCandidates = "";
        @JsonProperty("LS_GMY")
        @JsonAlias({"ELY_DSU_AMT", "SIL_AMT"})
        @Builder.Default
        private String amount = "";
        @JsonProperty("MM_RFE")
        @JsonAlias("RFE")
        @Builder.Default
        private String rentFee = "";
        @JsonProperty("MM_RFE_ETC")
        @Builder.Default
        private String rentFeeEtc = "";
    }

    @Getter
    @NoArgsConstructor
    public static class Label extends SupplyInfo {
        public Label(String address, String detailAddress, String houseInfo, String houseTypeName, String supplyArea, String netLeasableArea, String numberOfHousehold, String numberOfSupplyHousehold, String numberOfApplicants, String numberOfCandidates, String amount, String rentFee, String rentFeeEtc) {
            super(address, detailAddress, houseInfo, houseTypeName, supplyArea, netLeasableArea, numberOfHousehold, numberOfSupplyHousehold, numberOfApplicants, numberOfCandidates, amount, rentFee, rentFeeEtc);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Value extends SupplyInfo {
        public Value(String address, String detailAddress, String houseInfo, String houseTypeName, String supplyArea, String netLeasableArea, String numberOfHousehold, String numberOfSupplyHousehold, String numberOfApplicants, String numberOfCandidates, String amount, String rentFee, String rentFeeEtc) {
            super(address, detailAddress, houseInfo, houseTypeName, supplyArea, netLeasableArea, numberOfHousehold, numberOfSupplyHousehold, numberOfApplicants, numberOfCandidates, amount, rentFee, rentFeeEtc);
        }
    }

    public static LHAnnouncementSupplyInfoResponse jsonToObject(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(json, LHAnnouncementSupplyInfoResponse.class);
        } catch (JsonProcessingException e) {
            throw new GeneralException(e);
        }
    }
}
