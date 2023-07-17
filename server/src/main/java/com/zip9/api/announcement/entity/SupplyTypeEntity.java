package com.zip9.api.announcement.entity;

import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Table(name = "TB_SUPPLY_TYPE")
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SupplyTypeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUPPLY_TYPE_ID", nullable = false)
    @Comment("공급 유형 ID")
    private Integer id;
    @Column(name = "SUPPLY_TYPE_CODE", nullable = false, length = 30)
    @Comment("공급 유형 코드")
    private String code;
    @Column(name = "SUPPLY_TYPE_NAME", nullable = false, length = 30)
    @Comment("공급 유형명")
    private String name;
}
