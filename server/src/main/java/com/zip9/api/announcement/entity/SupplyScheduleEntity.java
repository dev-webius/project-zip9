package com.zip9.api.announcement.entity;

import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Table(name = "TB_SUPPLY_SCHEDULE",  indexes = {
        @Index(name = "IDX_SUPPLY_SCHEDULE_01", columnList = "CREATED_AT", unique = false),
        @Index(name = "IDX_SUPPLY_SCHEDULE_02", columnList = "MODIFIED_AT", unique = false),
        @Index(name = "IDX_SUPPLY_SCHEDULE_03", columnList = "ANNOUNCEMENT_ID", unique = false),
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupplyScheduleEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUPPLY_SCHEDULE_ID", nullable = false)
    private Long id;
    @Column(name = "TARGET", length = 200)
    @Comment("공급대상")
    private String target;
    @Column(name = "APPLICATION_DATE", length = 100)
    @Comment("신청일시")
    private String applicationDatetime;
    @Column(name = "APPLICATION_METHOD", length = 100)
    @Comment("신청방법")
    private String applicationMethod;
    @Column(name = "WINNER_ANNOUNCEMENT_DATE", length = 100)
    @Comment("당첨자 발표일")
    private String winnerAnnouncementDate;
    @Column(name = "PAPER_SUBMIT_OPEN_DATE", length = 100)
    @Comment("서류제출 대상자 발표일")
    private String paperSubmitOpenAnnouncementDate;
    @Column(name = "PAPER_SUBMIT_TERM", length = 100)
    @Comment("서류제출기간")
    private String paperSubmitTerm;
    @Column(name = "CONTRACT_TERM", length = 100)
    @Comment("계약체결기간")
    private String contractTerm;
    @Column(name = "APPLICATION_TERM", length = 100)
    @Comment("접수기간")
    private String applicationTerm;
    @Column(name = "HOUSE_BROWSE_TERM", length = 100)
    @Comment("주택열람기간")
    private String houseBrowseTerm;
    @Column(name = "SUPPLY_SCHEDULE_GUIDE", length = 1000)
    @Comment("공급일정 안내사항")
    private String supplyScheduleGuide;

    @ManyToOne
    @JoinColumn(name = "ANNOUNCEMENT_ID")
    @Comment("공고 ID")
    private AnnouncementEntity announcement;

    @Builder(builderMethodName = "ByAnnouncementDetailSupplyScheduleBuilder")
    public SupplyScheduleEntity(AnnouncementDetailResponse.SupplySchedule supplySchedule, AnnouncementEntity announcement) {
        this.target = supplySchedule.getTarget();
        this.applicationDatetime = supplySchedule.getApplicationDatetime();
        this.applicationMethod = supplySchedule.getApplicationMethod();
        this.winnerAnnouncementDate = supplySchedule.getWinnerAnnouncementDate();
        this.paperSubmitOpenAnnouncementDate = supplySchedule.getPaperSubmitOpenAnnouncementDate();
        this.paperSubmitTerm = supplySchedule.getPaperSubmitTerm();
        this.contractTerm = supplySchedule.getContractTerm();
        this.applicationTerm = supplySchedule.getApplicationTerm();
        this.houseBrowseTerm = supplySchedule.getHouseBrowseTerm();
        this.supplyScheduleGuide = supplySchedule.getSupplyScheduleGuide();
        this.announcement = announcement;
    }
}
