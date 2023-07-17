package com.zip9.api.announcement.entity;

import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Table(name = "TB_ANNOUNCEMENT_STATUS")
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementStatusEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANNOUNCEMENT_STATUS_ID", nullable = false)
    @Comment("공고 상태 ID")
    private Integer id;
    @Column(name = "ANNOUNCEMENT_STATUS_CODE", nullable = false, length = 30)
    @Comment("공고 상태 코드")
    private String code;
    @Column(name = "ANNOUNCEMENT_STATUS_NAME", nullable = false, length = 30)
    @Comment("공고 상태명")
    private String name;
}
