package com.zip9.api.announcement.entity;

import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Table(name = "TB_QUALIFICATION", indexes = {
        @Index(name = "IDX_ANNOUNCEMENT_01", columnList = "CREATED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_02", columnList = "MODIFIED_AT", unique = false),
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QualificationEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUALIFICATION_ID", nullable = false)
    private Long id;

    @Column(name = "QUALIFICATION_TYPE_NAME", length = 100)
    @Comment("신청자격 구분명")
    private String qualificationTypeName;

    @Column(name = "REQUIREMENT", length = 1000)
    @Comment("신청자격 세부자격요건")
    private String requirement;

    @ManyToOne
    @JoinColumn(name = "ANNOUNCEMENT_ID")
    @Comment("공고 ID")
    private AnnouncementEntity announcement;

    @Builder(builderMethodName = "ByAnnouncementDetailQualificationBuilder")
    public QualificationEntity(AnnouncementDetailResponse.Qualification qualification, AnnouncementEntity announcement) {
        this.qualificationTypeName = qualification.getQualificationTypeName();
        this.requirement = qualification.getRequirement();
        this.announcement = announcement;
    }
}
