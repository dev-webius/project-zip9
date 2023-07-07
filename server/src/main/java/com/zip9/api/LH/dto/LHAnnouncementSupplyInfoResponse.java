package com.zip9.api.LH.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LHAnnouncementSupplyInfoResponse {
    @Builder.Default
    private List<Label> labels = new ArrayList<>();
    @Builder.Default
    private List<List<Value>> values = new ArrayList<>();

    @JsonSetter("dsList01Nm")
    @JsonAlias({"dsList02Nm", "dsList03Nm"})
    public void setLabels(List<Label> labels) {
        this.labels.addAll(labels);
    }

    @JsonSetter("dsList01")
    @JsonAlias({"dsList02", "dsList03"})
    public void setValues(List<Value> values) {
        this.values.add(values);
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
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
    public static class Label extends Item {
        public Label(List<String> addresses, String houseInfo, String houseTypeName, String supplyArea, String netLeasableArea, String numberOfHousehold, String numberOfSupplyHousehold, String numberOfApplicants, String numberOfCandidates, String amount, String rentFee, String rentFeeEtc) {
            super(addresses, houseInfo, houseTypeName, supplyArea, netLeasableArea, numberOfHousehold, numberOfSupplyHousehold, numberOfApplicants, numberOfCandidates, amount, rentFee, rentFeeEtc);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Value extends Item {
        public Value(List<String> addresses, String houseInfo, String houseTypeName, String supplyArea, String netLeasableArea, String numberOfHousehold, String numberOfSupplyHousehold, String numberOfApplicants, String numberOfCandidates, String amount, String rentFee, String rentFeeEtc) {
            super(addresses, houseInfo, houseTypeName, supplyArea, netLeasableArea, numberOfHousehold, numberOfSupplyHousehold, numberOfApplicants, numberOfCandidates, amount, rentFee, rentFeeEtc);
        }
    }

}
