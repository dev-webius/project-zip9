package com.zip9.api.announcement.entity;

import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Table(name = "TB_ETC", indexes = {
        @Index(name = "IDX_ANNOUNCEMENT_01", columnList = "CREATED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_02", columnList = "MODIFIED_AT", unique = false),
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EtcEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ETC_ID", nullable = false)
    private Long id;
    @Column(name = "COMMENT", length = 1000)
    @Comment("기타사항")
    private String comment;
    @Column(name = "ANNOUNCEMENT_DESCRIPTION", length = 1000)
    @Comment("공고내용")
    private String announcementDescription;
    @Column(name = "CORRECT_OR_CANCEL_REASON", length = 1000)
    @Comment("정정/취소사유")
    private String correctOrCancelReason;
    @Column(name = "TARGET_AREA", length = 200)
    @Comment("모집지역")
    private String targetArea;
    @Column(name = "TARGET_HOUSE", length = 200)
    @Comment("대상주택")
    private String targetHouse;
    @Column(name = "LEASE_TERMS", length = 100)
    @Comment("임대기간")
    private String leaseTerms;
    @Column(name = "LEASE_CONDITION", length = 500)
    @Comment("임대조건")
    private String leaseCondition;
    @Column(name = "CAUTION", length = 1000)
    @Comment("주의사항")
    private String caution;
    @Column(name = "SUPPORT_LIMIT_AMOUNT")
    @Comment("지원한도액")
    private Integer supportLimitAmount;
    @Column(name = "NUMBER_OF_SUPPLY_HOUSEHOLD")
    @Comment("공급호수")
    private Integer numberOfSupplyHousehold;
    @Column(name = "RECEPTION_ADDRESS", length = 100)
    @Comment("주택열람 기간")
    private String receptionAddress;
    @Column(name = "GROUP_HOME_AGENCY", length = 200)
    @Comment("공동생활가정 운영기관")
    private String groupHomeAgency;

    @ManyToOne
    @JoinColumn(name = "ANNOUNCEMENT_ID")
    @Comment("공고 ID")
    private AnnouncementEntity announcement;

    @Builder(builderMethodName = "ByAnnouncementDetailEtcBuilder")
    public EtcEntity(AnnouncementDetailResponse.Etc etc, AnnouncementEntity announcement) {
        this.comment = etc.getComment();
        this.announcementDescription = etc.getAnnouncementDescription();
        this.correctOrCancelReason = etc.getCorrectOrCancelReason();
        this.targetArea = etc.getTargetArea();
        this.targetHouse = etc.getTargetHouse();
        this.leaseTerms = etc.getLeaseTerms();
        this.leaseCondition = etc.getLeaseCondition();
        this.caution = etc.getCaution();
        this.supportLimitAmount = etc.getSupportLimitAmount();
        this.numberOfSupplyHousehold = etc.getNumberOfSupplyHousehold();
        this.receptionAddress = etc.getReceptionAddress();
        this.groupHomeAgency = etc.getGroupHomeAgency();
        this.announcement = announcement;
    }
}
