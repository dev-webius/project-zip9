package com.zip9.api.announcement.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.zip9.api.LH.enums.City;
import com.zip9.api.announcement.entity.HouseComplexPositionEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AnnouncementResponse {
    private Meta meta;
    @JsonUnwrapped
    private Item item;

    public AnnouncementResponse() {
        meta = new Meta();
        item = new Item();
    }

    @Getter
    public static class Item {
        private LinkedHashMap<String, List<Announcement>> announcements = new LinkedHashMap<>();

        public Item() {
            Arrays.stream(City.values())
                    .forEach(city -> announcements.put(city.shortName, new ArrayList<>()));
        }
    }

    @Getter
    public static class Meta {
        @Schema(description = "도시별 공고 수")
        private LinkedHashMap<String, Integer> numberOfAnnouncementsByCity = new LinkedHashMap<>();

        public Meta() {
            Arrays.stream(City.values())
                    .forEach(city -> numberOfAnnouncementsByCity.put(
                            city.shortName, 0
                    ));
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        @Schema(description = "단지명")
        String houseComplexName;
        @Schema(description = "주소")
        String address;
        @Schema(description = "경도")
        String x;
        @Schema(description = "위도")
        String y;

        public static Position buildFrom(HouseComplexPositionEntity entity) {
            if (ObjectUtils.isEmpty(entity)) {
                return new Position();
            } else {
                return Position.builder()
                        .houseComplexName(AnnouncementDetailResponse.HouseComplex.buildFrom(entity.getHouseComplex()).getNameOrDetailAddress())
                        .address(entity.getRoadAddress())
                        .x(entity.getX())
                        .y(entity.getY())
                        .build();
            }
        }

    }
}
