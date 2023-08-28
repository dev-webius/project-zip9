package com.zip9.api.announcement.entity;

import com.zip9.api.LH.dto.LHAnnouncementResponse;
import com.zip9.api.LH.enums.*;
import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "TB_ANNOUNCEMENT", indexes = {
        @Index(name = "UDX_ANNOUNCEMENT_01", columnList = "THIRD_PARTY_ANNOUNCEMENT_ID", unique = true),
        @Index(name = "IDX_ANNOUNCEMENT_01", columnList = "CREATED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_02", columnList = "MODIFIED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_03", columnList = "ANNOUNCED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_04", columnList = "CLOSED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_05", columnList = "TITLE", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_06", columnList = "STATUS_CODE", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_07", columnList = "CITY_CODE", unique = false),
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANNOUNCEMENT_ID", nullable = false)
    private Long id;
    @Column(name = "THIRD_PARTY_ANNOUNCEMENT_ID", length = 100, nullable = false)
    @Comment("타사 공고 ID")
    private String thirdPartyId;
    @Column(name = "THIRD_PARTY_NAME", nullable = false, length = 20)
    @Comment("타사명")
    private String thirdPartyName;
    @Column(name = "TITLE", nullable = false, length = 200)
    @Comment("공고명")
    private String title;
    @Column(name = "STATUS_CODE", nullable = false, length = 30)
    @Comment("공고 상태 코드")
    private String statusCode;
    @Column(name = "TYPE_CODE", nullable = false, length = 50)
    @Comment("공고 유형 코드")
    private String typeCode;
    @Column(name = "DETAIL_TYPE_CODE", nullable = false, length = 50)
    @Comment("공고 상세 유형 코드")
    private String detailTypeCode;
    @Column(name = "SUPPLY_TYPE_CODE", nullable = false, length = 50)
    @Comment("공급 유형 코드")
    private String supplyTypeCode;
    @Column(name = "CITY_CODE", nullable = false, length = 50)
    @Comment("도시 코드")
    private String cityCode;
    @Column(name = "CS_TYPE_CODE", nullable = true, length = 50)
    @Comment("고객센터 유형 코드")
    private String csTypeCode;
    @Column(name = "DETAIL_URL", nullable = false, length = 255)
    @Comment("공고문 URL")
    private String detailUrl;
    @Column(name = "DETAIL_URL_MOBILE", nullable = false, length = 255)
    @Comment("공고문 URL(모바일)")
    private String detailUrlMobile;
    @Column(name = "REGISTERED_AT", nullable = false)
    @Comment("등록일")
    private LocalDateTime registeredAt;
    @Column(name = "ANNOUNCED_AT", nullable = false)
    @Comment("공고일")
    private LocalDateTime announcedAt;
    @Column(name = "CLOSED_AT", nullable = false)
    @Comment("마감일")
    private LocalDateTime closedAt;
    @Column(name = "USED", nullable = false)
    @Comment("사용여부")
    private Boolean used;

    @OneToMany(mappedBy = "announcement", fetch = FetchType.LAZY)
    private List<HouseComplexEntity> houseComplexes = new ArrayList<>();

    @Builder(builderMethodName = "ByLHAnnouncementBuilder")
    public AnnouncementEntity(LHAnnouncementResponse lhAnnouncement) {
        Assert.notNull(lhAnnouncement.getId(), "'id' cannot be null.");
        Assert.notNull(lhAnnouncement.getTitle(), "'title' cannot be null.");
        Assert.notNull(lhAnnouncement.getAnnouncementStatusName(), "'announcementStatusName' cannot be null.");
        Assert.notNull(lhAnnouncement.getAnnouncementTypeCode(), "'announcementTypeCode' cannot be null.");
        Assert.notNull(lhAnnouncement.getAnnouncementDetailTypeCode(), "'announcementDetailTypeCode' cannot be null.");
        Assert.notNull(lhAnnouncement.getSupplyTypeCode(), "'supplyTypeCode' cannot be null.");
        Assert.notNull(lhAnnouncement.getCityName(), "'cityName' cannot be null.");
        Assert.notNull(lhAnnouncement.getDetailUrl(), "'detailUrl' cannot be null.");
        Assert.notNull(lhAnnouncement.getDetailUrlMobile(), "'detailUrlMobile' cannot be null.");
        Assert.notNull(lhAnnouncement.getRegistDate(), "'registDate' cannot be null.");
        Assert.notNull(lhAnnouncement.getAnnouncementDate(), "'announcementDate' cannot be null.");
        Assert.notNull(lhAnnouncement.getCloseDate(), "'closeDate' cannot be null.");
        Assert.notNull(AnnouncementStatus.codeOf(lhAnnouncement.getAnnouncementStatusName()), "'announcementStatusName' is invalid.");
        Assert.notNull(AnnouncementType.codeOf(lhAnnouncement.getAnnouncementTypeCode()), "'announcementTypeCode' is invalid.");
        Assert.notNull(AnnouncementDetailType.codeOf(lhAnnouncement.getAnnouncementDetailTypeCode()), "'announcementDetailTypeCode' is invalid.");
        Assert.notNull(HouseSupplyType.codeOf(lhAnnouncement.getSupplyTypeCode()), "'supplyTypeCode' is invalid.");
        Assert.notNull(City.nameOf(lhAnnouncement.getCityName()), "'cityName' is invalid.");

        this.thirdPartyId = lhAnnouncement.getId();
        this.thirdPartyName = "LH";
        this.title = lhAnnouncement.getTitle();
        this.statusCode = AnnouncementStatus.codeOf(lhAnnouncement.getAnnouncementStatusName()).name();
        this.typeCode = AnnouncementType.codeOf(lhAnnouncement.getAnnouncementTypeCode()).code;
        this.detailTypeCode = AnnouncementDetailType.codeOf(lhAnnouncement.getAnnouncementDetailTypeCode()).code;
        this.supplyTypeCode = HouseSupplyType.codeOf(lhAnnouncement.getSupplyTypeCode()).code;
        this.cityCode = City.nameOf(lhAnnouncement.getCityName()).code;
        this.csTypeCode = lhAnnouncement.getCrmCode();
        this.detailUrl = lhAnnouncement.getDetailUrl();
        this.detailUrlMobile = lhAnnouncement.getDetailUrlMobile();
        this.registeredAt = LocalDateTime.of(lhAnnouncement.getRegistDate(), LocalTime.MIN);
        this.announcedAt = LocalDateTime.of(lhAnnouncement.getAnnouncementDate(), LocalTime.MIN);
        this.closedAt = LocalDateTime.of(lhAnnouncement.getCloseDate(), LocalTime.MAX);
        this.used = Boolean.TRUE;
    }

    public void close() {
        this.statusCode = AnnouncementStatus.CLOSED.name();
    }
}
