package com.zip9.api.announcement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zip9.api.LH.dto.LHAnnouncementResponse;
import com.zip9.api.LH.enums.*;
import com.zip9.api.announcement.entity.HouseComplexPositionEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class Announcement {
    @Schema(description = "공고 ID")
    private Long id;
    @Schema(description = "공고명")
    private String title;
    @Schema(description = "공고상세유형명")
    private String detailTypeName;
    @Schema(description = "공고게시일")
    private LocalDate announcedDate;
    @Schema(description = "공고마감일")
    private LocalDate closedDate;

    @Schema(description = "신청기간")
    private String applicationTerm;
    public String getApplicationTerm() {
        if (StringUtils.hasLength(announcedDate.toString()) && StringUtils.hasLength(closedDate.toString())) {
            return announcedDate.toString() + " ~ " + closedDate.toString();
        } else {
            return "";
        }
    }

    @Schema(description = "단지별 위치정보")
    private List<Position> positions;

    @JsonIgnore
    @Schema(description = "타사 공고 ID")
    private String thirdPartyId;
    @JsonIgnore
    @Schema(description = "공고상태명")
    private String statusName;
    @JsonIgnore
    @Schema(description = "공고유형명")
    private String typeName;
    @JsonIgnore
    @Schema(description = "도시명")
    private String cityName;
    @JsonIgnore
    @Schema(description = "도시명(축약형)")
    private String cityShortName;
    @JsonIgnore
    @Schema(description = "공고문 URL")
    private String detailUrl;
    @JsonIgnore
    @Schema(description = "공고문 URL(모바일)")
    private String detailUrlMobile;
    @JsonIgnore
    @Schema(description = "공급유형")
    private String supplyType;

    @Builder(builderClassName = "ByLHAnnouncement", builderMethodName = "ByLHAnnouncement")
    public Announcement(LHAnnouncementResponse lhAnnouncement, List<Position> positions) {
        Assert.notNull(City.nameOf(lhAnnouncement.getCityName()), "'city' is invalid.");
        Assert.notNull(AnnouncementType.codeOf(lhAnnouncement.getAnnouncementTypeCode()), "'announcementType' is invalid.");
        Assert.notNull(AnnouncementDetailType.codeOf(lhAnnouncement.getAnnouncementDetailTypeCode()), "'announcementDetailType' is invalid.");
        Assert.notNull(HouseSupplyType.codeOf(lhAnnouncement.getSupplyTypeCode()), "'houseSupplyType' is invalid.");

        this.thirdPartyId = lhAnnouncement.getId();
        this.title = lhAnnouncement.getTitle();
        this.statusName = lhAnnouncement.getAnnouncementStatusName();
        this.typeName = lhAnnouncement.getAnnouncementTypeName();
        this.detailTypeName = lhAnnouncement.getAnnouncementDetailTypeName();
        this.cityName = lhAnnouncement.getCityName();
        this.cityShortName = City.nameOf(lhAnnouncement.getCityName()).shortName;
        this.detailUrlMobile = lhAnnouncement.getDetailUrlMobile();
        this.detailUrl = lhAnnouncement.getDetailUrl();
        this.announcedDate = lhAnnouncement.getRegistDate();
        this.closedDate = lhAnnouncement.getCloseDate();
        this.positions = positions;
        this.supplyType = HouseSupplyType.codeOf(lhAnnouncement.getSupplyTypeCode()).name();
    }

    public Announcement(Long id, String thirdPartyId, String title, String statusCode, String announcementTypeCode, String announcementDetailTypeCode, String cityCode, String detailUrl, String detailUrlMobile, LocalDateTime announcedAt, LocalDateTime closedAt, String supplyTypeCode) {
        this.id = id;
        this.thirdPartyId = thirdPartyId;
        this.title = title;
        this.statusName = AnnouncementStatus.valueOf(statusCode).name;
        this.typeName = AnnouncementType.codeOf(announcementTypeCode).name;
        this.detailTypeName = AnnouncementDetailType.codeOf(announcementDetailTypeCode).name;
        this.cityName = City.codeOf(cityCode).name;
        this.cityShortName = City.codeOf(cityCode).shortName;
        this.detailUrl = detailUrl;
        this.detailUrlMobile = detailUrlMobile;
        this.announcedDate = announcedAt.toLocalDate();
        this.closedDate = closedAt.toLocalDate();
        this.supplyType = HouseSupplyType.codeOf(supplyTypeCode).name;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
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
