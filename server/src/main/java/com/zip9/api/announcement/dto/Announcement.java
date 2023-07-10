package com.zip9.api.announcement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    @JsonIgnore
    private String id;
    @Parameter(description = "공고명")
    private String title;
    @Parameter(description = "공고상태명")
    private String statusName;
    @Parameter(description = "공고유형명")
    private String announcementTypeName;
    @Parameter(description = "공고상세유형명")
    private String announcementDetailTypeName;
    @Parameter(description = "도시명")
    private String cityName;
    @Parameter(description = "도시명(축약형)")
    private String cityShortName;
    @Parameter(description = "공고문 URL")
    private String detailUrl;
    @Parameter(description = "공고문 URL(모바일)")
    private String detailUrlMobile;
    @Parameter(description = "공고게시일")
    private LocalDate registDate;
    @Parameter(description = "공고마감일")
    private LocalDate closeDate;
    @Parameter(description = "단지별 위치정보")
    private List<AnnouncementResponse.Position> positions;
    @Parameter(description = "단지별 상세정보")
    private List<AnnouncementDetailsResponse> details;
}
