package com.zip9.api.announcement.entity;

import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Table(name = "TB_ANNOUNCEMENT_DETAIL_TYPE")
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDetailTypeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANNOUNCEMENT_TYPE_ID", nullable = false)
    @Comment("공고 상세유형 ID")
    private Integer id;
    @Column(name = "ANNOUNCEMENT_TYPE_CODE", nullable = false, length = 3)
    @Comment("공고 유형 코드")
    private String upperCode;
    @Column(name = "ANNOUNCEMENT_DETAIL_TYPE_CODE", nullable = false, length = 3)
    @Comment("공고 상세 유형 코드")
    private String code;
    @Column(name = "ANNOUNCEMENT_DETAIL_TYPE_NAME", nullable = false, length = 50)
    @Comment("공고 상세 유형명")
    private String name;
}
