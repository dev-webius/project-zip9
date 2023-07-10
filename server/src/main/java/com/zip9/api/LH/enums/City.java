package com.zip9.api.LH.enums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum City {
    SEOUL("11", "서울특별시", "서울"),
    BUSAN("26", "부산광역시", "부산"),
    DAEGU("27", "대구광역시", "대구"),
    INCHEON("28", "인천광역시", "인천"),
    GWANGJU("29", "광주광역시", "광주"),
    DAEJEON("30", "대전광역시", "대전"),
    ULSAN("31", "울산광역시", "울산"),
    SEJONG("36110", "세종특별자치시", "세종"),
    GYEONGGI("41", "경기도", "경기"),
    GANGWON("42", "강원특별자치도", "강원"),
    CHUNGNAM("43", "충청북도", "충북"),
    CHUNGBUK("44", "충청남도", "충남"),
    JEONNBUK("45", "전라북도", "전북"),
    JEONNAM("46", "전라남도", "전남"),
    GYEONGBUK("47", "경상북도", "경북"),
    GYEONGNAM("48", "경상남도", "경남"),
    JEJU("50", "제주특별자치도", "제주"),
    ETC("99", "기타", "기타");

    public final String code;
    public final String name;
    public final String shortName;


    City(String code, String name, String shortName) {
        this.code = code;
        this.name = name;
        this.shortName = shortName;
    }

    static public City codeOf(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findAny()
                .orElse(null);
    }

    static public City nameOf(String name) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(name))
                .findAny()
                .orElse(null);
    }
}
