package com.zip9.api.announcement.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.zip9.api.LH.enums.City;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        @Parameter(description = "단지명")
        String houseComplexName;
        @Parameter(description = "주소")
        String address;
        @Parameter(description = "경도")
        String x;
        @Parameter(description = "위도")
        String y;
    }
}
