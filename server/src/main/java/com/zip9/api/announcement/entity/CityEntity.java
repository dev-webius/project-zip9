package com.zip9.api.announcement.entity;

import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Table(name = "TB_CITY")
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CityEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CITY_ID", nullable = false)
    @Comment("도시 ID")
    private Integer id;
    @Column(name = "CITY_CODE", nullable = false, length = 5)
    @Comment("도시 코드")
    private String code;
    @Column(name = "CITY_NAME", nullable = false, length = 30)
    @Comment("도시 한글명")
    private String name;
    @Column(name = "CITY_EN_NAME", nullable = false, length = 30)
    @Comment("도시 영문명")
    private String englishName;
    @Column(name = "CITY_SHORT_NAME", nullable = false, length = 10)
    @Comment("도시 한글명(축약)")
    private String shortName;
}
