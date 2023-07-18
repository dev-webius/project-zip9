package com.zip9.api.announcement.entity;

import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Table(name = "TB_RECEPTION", indexes = {
        @Index(name = "IDX_ANNOUNCEMENT_01", columnList = "CREATED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_02", columnList = "MODIFIED_AT", unique = false),
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReceptionEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECEPTION_ID", nullable = false)
    private Long id;
    @Column(name = "ADDRESS", length = 200)
    @Comment("주소")
    private String address;
    @Column(name = "TELEPHONE_NUMBER", length = 100)
    @Comment("전화번호")
    private String telephoneNumber;
    @Column(name = "OPERATION_TERM", length = 100)
    @Comment("운영기간")
    private String operationTerm;
    @Column(name = "SCHEDULE_GUIDE", length = 1000)
    @Comment("일정내용")
    private String scheduleGuide;
    @Column(name = "RECEPTION_GUIDE", length = 1000)
    @Comment("안내사항")
    private String receptionGuide;

    @OneToOne
    @JoinColumn(name = "ANNOUNCEMENT_ID")
    @Comment("공고 ID")
    private AnnouncementEntity announcement;

    @Builder(builderMethodName = "ByAnnouncementDetailReceptionBuilder")
    public ReceptionEntity(AnnouncementDetailResponse.Reception reception, AnnouncementEntity announcement) {
        this.address = reception.getAddress();
        this.telephoneNumber = reception.getTelephoneNumber();
        this.operationTerm = reception.getOperationTerm();
        this.scheduleGuide = reception.getScheduleGuide();
        this.receptionGuide = reception.getReceptionGuide();
        this.announcement = announcement;
    }
}
