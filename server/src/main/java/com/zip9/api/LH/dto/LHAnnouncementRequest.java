package com.zip9.api.LH.dto;

import com.zip9.api.announcement.dto.AnnouncementRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class LHAnnouncementRequest {
    private LocalDate registStartDate;
    private LocalDate registEndDate;
    private String title;
    private String announcementType;
    private String announcementStatus;
    private String city;
    private LocalDate closeStartDate;
    private LocalDate closeEndDate;
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10000;
}
