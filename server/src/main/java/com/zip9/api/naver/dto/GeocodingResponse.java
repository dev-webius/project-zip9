package com.zip9.api.naver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeocodingResponse {
    @JsonProperty("addresses")
    private List<Address> addresses;

    @JsonIgnore
    @JsonProperty("meta")
    private Meta meta;

    @JsonIgnore
    @JsonProperty("status")
    private String status;

    @JsonIgnore
    @JsonProperty("errorMessage")
    private String errorMessage;

    @Getter
    public static class Meta {
        private Integer totalCount;
        private Integer page;
        private Integer count;
    }

    @Getter
    @NoArgsConstructor
    public static class Address {
        private String roadAddress;
        private String jibunAddress;
        private String englishAddress;
        private String x;
        private String y;
        private Double distance;
        private List<AddressElelment> addressElements;

        public String getAddress() {
            if (StringUtils.hasLength(roadAddress)) {
                return roadAddress;
            } else if (StringUtils.hasLength(jibunAddress)) {
                return jibunAddress;
            } else {
                return "조회되지 않음";
            }
        }

        public Boolean hasPosition() {
            return StringUtils.hasText(this.x) && StringUtils.hasText(this.y);
        }

        @Getter
        public static class AddressElelment {
            List<String> types;
            String longName;
            String shortName;
            String code;
        }
    }

}
