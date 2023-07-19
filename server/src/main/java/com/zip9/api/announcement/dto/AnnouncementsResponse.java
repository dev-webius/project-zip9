package com.zip9.api.announcement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zip9.api.LH.enums.City;
import com.zip9.api.common.enums.Code;
import com.zip9.api.common.exception.GeneralException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AnnouncementsResponse {
    private List<Item> items;
    @JsonIgnore
    private Meta meta;

    public AnnouncementsResponse() {
        items = new ArrayList<>();
        meta = new Meta();

        for (City city : City.values()) {
            items.add(new Item(city.name(), new ArrayList<>()));
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Item {
        @Schema(description = "도시")
        private String city;

        @Schema(description = "공고 목록")
        private List<Announcement> announcements;

        public boolean equals(String city) {
            return city.equalsIgnoreCase(this.city);
        }

        public void addAnnouncement(Announcement announcement) {
            announcements.add(announcement);
        }
    }

    public void addAnnouncement(Announcement announcement) {
        String city = City.nameOf(announcement.getCityName()).name();

        items.stream()
                .filter(it -> it.equals(city))
                .findAny()
                .orElseThrow(
                        () -> new GeneralException(Code.INTERNAL_ERROR, announcement.getId() + "번 공고 도시명이 올바르지 않습니다. [" + city + "]")
                )
                .addAnnouncement(announcement);
    }

    public void setTotal(Long total) {
        meta.setTotal(total);
    }
}
