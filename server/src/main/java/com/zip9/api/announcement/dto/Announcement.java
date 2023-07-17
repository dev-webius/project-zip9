package com.zip9.api.announcement.dto;

import com.zip9.api.LH.dto.LHAnnouncementResponse;
import com.zip9.api.LH.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@NoArgsConstructor
public class Announcement {
    @Schema(description = "공고 ID")
    private String id;
    @Schema(description = "공고명")
    private String title;
    @Schema(description = "공고상태명")
    private String statusName;
    @Schema(description = "공고유형명")
    private String announcementTypeName;
    @Schema(description = "공고상세유형명")
    private String announcementDetailTypeName;
    @Schema(description = "도시명")
    private String cityName;
    @Schema(description = "도시명(축약형)")
    private String cityShortName;
    @Schema(description = "공고문 URL")
    private String detailUrl;
    @Schema(description = "공고문 URL(모바일)")
    private String detailUrlMobile;
    @Schema(description = "공고게시일")
    private LocalDate announcedDate;
    @Schema(description = "공고마감일")
    private LocalDate closedDate;
    @Schema(description = "단지별 위치정보")
    private List<AnnouncementResponse.Position> positions;
    @Schema(description = "공급유형")
    private String supplyType;
//    @Schema(description = "공고유형")
//    private String announcementType;
//    @Schema(description = "공고상세유형")
//    private String announcementDetailType;
//    @Schema(description = "고객센터유형코드")
//    private String csTypeCode;

    @Builder(builderClassName = "ByLHAnnouncement", builderMethodName = "ByLHAnnouncement")
    public Announcement(LHAnnouncementResponse lhAnnouncement, List<AnnouncementResponse.Position> positions) {
        Assert.notNull(City.nameOf(lhAnnouncement.getCityName()), "'city' is invalid.");
        Assert.notNull(AnnouncementType.codeOf(lhAnnouncement.getAnnouncementTypeCode()), "'announcementType' is invalid.");
        Assert.notNull(AnnouncementDetailType.codeOf(lhAnnouncement.getAnnouncementDetailTypeCode()), "'announcementDetailType' is invalid.");
        Assert.notNull(HouseSupplyType.codeOf(lhAnnouncement.getSupplyTypeCode()), "'houseSupplyType' is invalid.");

        this.id = lhAnnouncement.getId();
        this.title = lhAnnouncement.getTitle();
        this.statusName = lhAnnouncement.getAnnouncementStatusName();
        this.announcementTypeName = lhAnnouncement.getAnnouncementTypeName();
        this.announcementDetailTypeName = lhAnnouncement.getAnnouncementDetailTypeName();
        this.cityName = lhAnnouncement.getCityName();
        this.cityShortName = City.nameOf(lhAnnouncement.getCityName()).shortName;
        this.detailUrlMobile = lhAnnouncement.getDetailUrlMobile();
        this.detailUrl = lhAnnouncement.getDetailUrl();
        this.announcedDate = lhAnnouncement.getRegistDate();
        this.closedDate = lhAnnouncement.getCloseDate();
        this.positions = positions;
        this.supplyType = HouseSupplyType.codeOf(lhAnnouncement.getSupplyTypeCode()).name();

//        this.announcementType = AnnouncementType.codeOf(lhAnnouncement.getAnnouncementTypeCode()).name();
//        this.announcementDetailType = AnnouncementDetailType.codeOf(lhAnnouncement.getAnnouncementDetailTypeCode()).name();
//        this.csTypeCode = lhAnnouncement.getCrmCode();
    }

    public Announcement(String id, String title, String statusCode, String announcementTypeCode, String announcementDetailTypeCode, String cityCode, String detailUrl, String detailUrlMobile, LocalDateTime announcedAt, LocalDateTime closedAt, String supplyTypeCode) {
        this.id = id;
        this.title = title;
        this.statusName = AnnouncementStatus.valueOf(statusCode).name;
        this.announcementTypeName = AnnouncementType.codeOf(announcementTypeCode).name;
        this.announcementDetailTypeName = AnnouncementDetailType.codeOf(announcementDetailTypeCode).name;
        this.cityName = City.codeOf(cityCode).name;
        this.cityShortName = City.codeOf(cityCode).shortName;
        this.detailUrl = detailUrl;
        this.detailUrlMobile = detailUrlMobile;
        this.announcedDate = announcedAt.toLocalDate();
        this.closedDate = closedAt.toLocalDate();
        this.supplyType = HouseSupplyType.codeOf(supplyTypeCode).name;
    }

    public String getCityShortName() {
        if (ObjectUtils.isEmpty(City.nameOf(cityName))) {
            return City.ETC.shortName;
        } else {
            return City.nameOf(cityName).shortName;
        }
    }

    public void setCityShortName(String cityShortName) {
        this.cityShortName = cityShortName;
    }
}
