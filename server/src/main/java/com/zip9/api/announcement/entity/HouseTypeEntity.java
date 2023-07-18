package com.zip9.api.announcement.entity;

import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Table(name = "TB_HOUSE_TYPE", indexes = {
        @Index(name = "IDX_HOUSE_TYPE_01", columnList = "CREATED_AT", unique = false),
        @Index(name = "IDX_HOUSE_TYPE_02", columnList = "MODIFIED_AT", unique = false),
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseTypeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOUSE_TYPE_ID", nullable = false)
    private Long id;
    @Column(name = "HOUSE_TYPE_NAME")
    @Comment("주택유형명")
    private String houseTypeName;
    @Column(name = "SUPPLY_AREA")
    @Comment("공급면적")
    private String supplyArea;
    @Column(name = "NET_LEASABLE_AREA")
    @Comment("전용면적")
    private String netLeasableArea;
    @Column(name = "NUMBER_OF_HOUSEHOLD")
    @Comment("세대수")
    private Integer numberOfHousehold;
    @Column(name = "NUMBER_OF_SUPPLY_HOUSEHOLD")
    @Comment("공급세대수")
    private Integer numberOfSupplyHousehold;
    @Column(name = "NUMBER_OF_APPLICANT")
    @Comment("모집인원")
    private Integer numberOfApplicants;
    @Column(name = "NUMBER_OF_CANDIDATE")
    @Comment("예비자수")
    private Integer numberOfCandidates;
    @Column(name = "AMOUNT")
    @Comment("임대보증금")
    private String amount;
    @Column(name = "RENT_FEE")
    @Comment("월임대료")
    private String rentFee;
    @Column(name = "RENT_FEE_ETC")
    @Comment("월임대료(기타)")
    private String rentFeeEtc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_COMPLEX_ID")
    @Comment("단지 ID")
    private HouseComplexEntity houseComplex;

    @Builder(builderMethodName = "ByAnnouncementDetailHouseTypeBuilder")
    public HouseTypeEntity(AnnouncementDetailResponse.HouseType houseType, HouseComplexEntity houseComplex) {
        this.houseTypeName = houseType.getHouseTypeName();
        this.supplyArea = houseType.getSupplyArea();
        this.netLeasableArea = houseType.getNetLeasableArea();
        this.numberOfHousehold = houseType.getNumberOfHousehold();
        this.numberOfSupplyHousehold = houseType.getNumberOfSupplyHousehold();
        this.numberOfApplicants = houseType.getNumberOfApplicants();
        this.numberOfCandidates = houseType.getNumberOfCandidates();
        this.amount = houseType.getAmount();
        this.rentFee = houseType.getRentFee();
        this.rentFeeEtc = houseType.getRentFeeEtc();
        this.houseComplex = houseComplex;
    }
}
