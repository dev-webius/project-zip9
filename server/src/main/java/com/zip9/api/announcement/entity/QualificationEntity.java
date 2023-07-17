package com.zip9.api.announcement.entity;

import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
