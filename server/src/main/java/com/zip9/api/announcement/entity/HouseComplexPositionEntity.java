package com.zip9.api.announcement.entity;

import com.zip9.api.common.entity.BaseTimeEntity;
import com.zip9.api.naver.dto.GeocodingResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Table(name = "TB_HOUSE_COMPLEX_POSITION", indexes = {
        @Index(name = "IDX_HOUSE_COMPLEX_POSITION_01", columnList = "CREATED_AT", unique = false),
        @Index(name = "IDX_HOUSE_COMPLEX_POSITION_02", columnList = "MODIFIED_AT", unique = false),
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseComplexPositionEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOUSE_COMPLEX_POSITION_ID", nullable = false)
    private Long id;
    @Column(name = "ROAD_ADDRESS", length = 300)
    @Comment("도로명 주소")
    private String roadAddress;
    @Column(name = "JIBUN_ADDRESS", length = 300)
    @Comment("지번 주소")
    private String jibunAddress;
    @Column(name = "X", length = 50)
    @Comment("경도")
    private String x;
    @Column(name = "Y", length = 50)
    @Comment("위도")
    private String y;

    @OneToOne
    @JoinColumn(name = "HOUSE_COMPLEX_ID")
    @Comment("단지 ID")
    private HouseComplexEntity houseComplex;

    @Builder(builderMethodName = "ByAnnouncementDetailHouseComplexPositionBuilder")
    public HouseComplexPositionEntity(GeocodingResponse.Address address, HouseComplexEntity houseComplex) {
        this.roadAddress = address.getRoadAddress();
        this.jibunAddress = address.getJibunAddress();
        this.x = address.getX();
        this.y = address.getY();
        this.houseComplex = houseComplex;
        houseComplex.setHouseComplexPositionEntity(this);
    }
}
