package com.zip9.api.LH.dto;

import com.fasterxml.jackson.annotation.*;
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
    private Label label01;
    @JsonProperty("dsList02Nm")
    private Label label02;
    @JsonProperty("dsList03Nm")
    private Label label03;
    @JsonProperty("dsList01")
    @Builder.Default
    private List<Value> values01 = new ArrayList<>();
    @JsonProperty("dsList02")
    @Builder.Default
    private List<Value> values02 = new ArrayList<>();
    @JsonProperty("dsList03")
    @Builder.Default
    private List<Value> values03 = new ArrayList<>();

    @JsonSetter("dsList01Nm")
    public void setLabel01(List<Label> labels) {
        this.label01 = labels.stream().filter(ObjectUtils::isNotEmpty).findAny().orElse(null);
    }
    @JsonSetter("dsList02Nm")
    public void setLabel02(List<Label> labels) {
        this.label02 = labels.stream().filter(ObjectUtils::isNotEmpty).findAny().orElse(null);
    }
    @JsonSetter("dsList03Nm")
    public void setLabel03(List<Label> labels) {
        this.label03 = labels.stream().filter(ObjectUtils::isNotEmpty).findAny().orElse(null);
    }

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

    public Label getLabel() {
        if (!values01.isEmpty()) {
            return label01;
        } else if (!values02.isEmpty()) {
            return label02;
        } else if (!values03.isEmpty()) {
            return label03;
        } else {
            return new Label();
        }
    }

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
        @Builder.Default
        private List<String> addresses = new ArrayList<>();
        @JsonSetter("BZDT_NM")
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

        @JsonSetter("SBD_CNP_NM")
        @JsonAlias({"CNP_NM", "SGG_NM", "ARA", "ADR", "LTR_UNT_NM", "SBD_LGO_ADR", "DNG_HS_ADR"})
        public void setAddresses(String address) {
            this.addresses.add(address);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Label extends SupplyInfo {
        public Label(List<String> addresses, String houseInfo, String houseTypeName, String supplyArea, String netLeasableArea, String numberOfHousehold, String numberOfSupplyHousehold, String numberOfApplicants, String numberOfCandidates, String amount, String rentFee, String rentFeeEtc) {
            super(addresses, houseInfo, houseTypeName, supplyArea, netLeasableArea, numberOfHousehold, numberOfSupplyHousehold, numberOfApplicants, numberOfCandidates, amount, rentFee, rentFeeEtc);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Value extends SupplyInfo {
        public Value(List<String> addresses, String houseInfo, String houseTypeName, String supplyArea, String netLeasableArea, String numberOfHousehold, String numberOfSupplyHousehold, String numberOfApplicants, String numberOfCandidates, String amount, String rentFee, String rentFeeEtc) {
            super(addresses, houseInfo, houseTypeName, supplyArea, netLeasableArea, numberOfHousehold, numberOfSupplyHousehold, numberOfApplicants, numberOfCandidates, amount, rentFee, rentFeeEtc);
        }
    }

}
