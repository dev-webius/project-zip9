package com.zip9.api.LH.enums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
@Schema(enumAsRef = true)
public enum HouseSupplyType {
    LANDS("010", "토지"),
    PRE_SALE_HOUSE("050", "분양주택"),
    PRE_SALE_EXCHANGE("051", "분양전환"),
    PUBLIC_RENTAL_FOR_5_OR_10_YEARS("060", "공공임대(5년, 10년 분납임대)"),
    PUBLIC_RENTAL_FOR_50_YEARS("061", "임대주택-50년공공임대"),
    INTEGRATED_PUBLIC_RENTAL("062", "국민임대/장기전세/신축다세대/영구임대"),
    HAPPY_HOUSE("063", "행복주택"),
    HOME_BASED_CHILDCARE_CENTER("064", "가정어린이집"),
    YOUTH_NEWLYWEDS_BUY_TO_RENT("130", "청년신혼부부매입임대리츠"),
    YOUTH_BUY_TO_RENT("131", "청년매입임대"),
    NEWLYWEDS_BUY_TO_RENT("132", "신혼부부매입임대"),
    LESSOR_REMODELING("133", "집주인리모델링"),
    DORMITORY_BUY_TO_RENT("134", "기숙사형매입임대"),
    MULTI_UNIT_BUY_TO_RENT("135", "다가구매입임대"),
    BUY_TO_RENT_LONG_TERM_NOT_LEASED_HOUSE("136", "매입임대-장기미임대"),
    YOUTH_JEONSE_RENTAL("137", "청년전세임대"),
    NEWLYWEDS_JEONSE_RENTAL("138", "신혼부부전세임대"),
    EXISTING_HOUSE_JEONSE_RENTAL("139", "기존주택전세임대"),
    YOUTH_BUY_TO_RENT_ORDINARY("1315", "청년매입임대수시"),
    NEWLYWEDS_BUY_TO_RENT_ORDINARY("1325", "신혼부부매입임대수시"),
    MULTI_CHILD_JEONSE_RENTAL("140", "다자녀전세임대"),
    MULTI_CHILD_BUY_TO_RENT("141", "다자녀매입임대"),
    JEONSE_BUY_TO_RENT("143", "전세형매입임대"),
    NEWLYWEDS("390", "신혼희망타운"),
    INVALID_TYPE("000", "유효하지 않은 주택공급타입");

    public final String code;
    public final String name;

    HouseSupplyType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static HouseSupplyType codeOf(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findAny()
                .orElse(INVALID_TYPE);
    }
}
