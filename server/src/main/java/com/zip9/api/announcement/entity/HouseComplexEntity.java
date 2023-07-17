package com.zip9.api.announcement.entity;


import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Table(name = "TB_HOUSE_COMPLEX", indexes = {
        @Index(name = "IDX_ANNOUNCEMENT_01", columnList = "CREATED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_02", columnList = "MODIFIED_AT", unique = false),
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseComplexEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOUSE_COMPLEX_ID", nullable = false)
    private Long id;
    @Column(name = "NAME", length = 200)
    @Comment("단지명")
    private String name;
    @Column(name = "ADDRESS", length = 200)
    @Comment("주소")
    private String address;
    @Column(name = "DETAIL_ADDRESS", length = 200)
    @Comment("상세주소")
    private String detailAddress;
    @Column(name = "NET_LEASABLE_AREA_RANGE", length = 100)
    @Comment("전용면적 범위")
    private String netLeasableAreaRange;
    @Column(name = "TOTAL_OF_HOUSEHOLD")
    @Comment("총세대수")
    private Integer totalOfHousehold;
    @Column(name = "HEATING_TYPE_NAME", length = 100)
    @Comment("난방방식")
    private String heatingTypeName;
    @Column(name = "EXPECTED_MOVE_IN_DATE", length = 100)
    @Comment("입주예정월")
    private String expectedMoveInDate;
    @Column(name = "TRAFFIC_FACILITY", length = 255)
    @Comment("교통여건")
    private String trafficFacilities;
    @Column(name = "EDUCATION_FACILITY", length = 255)
    @Comment("교육환경")
    private String educationFacilities;
    @Column(name = "CONVENIENT_FACILITY", length = 255)
    @Comment("편의시설")
    private String convenientFacilities;
    @Column(name = "APPURTENANT_FACILITY", length = 255)
    @Comment("부대시설")
    private String appurtenantFacilities;
    @Column(name = "SUPPLY_GUIDE", length = 1000)
    @Comment("공급 안내사항")
    private String supplyInfoGuide;

    @ManyToOne
    @JoinColumn(name = "ANNOUNCEMENT_ID")
    @Comment("공고 ID")
    private AnnouncementEntity announcement;

    @Builder(builderMethodName = "ByAnnouncementDetailHouseComplexBuilder")
    public HouseComplexEntity(AnnouncementDetailResponse.HouseComplex houseComplex, AnnouncementEntity announcement) {
        this.name = houseComplex.getName();
        this.address = houseComplex.getAddress();
        this.detailAddress = houseComplex.getDetailAddress();
        this.netLeasableAreaRange = houseComplex.getNetLeasableAreaRange();
        this.totalOfHousehold = houseComplex.getTotalOfHousehold();
        this.heatingTypeName = houseComplex.getHeatingTypeName();
        this.expectedMoveInDate = houseComplex.getExpectedMoveInDate();
        this.trafficFacilities = houseComplex.getTrafficFacilities();
        this.educationFacilities = houseComplex.getEducationFacilities();
        this.convenientFacilities = houseComplex.getConvenientFacilities();
        this.appurtenantFacilities = houseComplex.getAppurtenantFacilities();
        this.supplyInfoGuide = houseComplex.getSupplyInfoGuide();
        this.announcement = announcement;
    }
}