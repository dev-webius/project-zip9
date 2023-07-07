package com.zip9.api.LH.enums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
@Schema(enumAsRef = true)
public enum AnnouncementDetailType {
    PRE_SALE_HOUSE("05", "분양주택"),
    INTEGRATED_PUBLIC_RENTAL("48", "통합공공임대"),
    NATIONAL_RENTAL("07", "국민임대"),
    PUBLIC_RENTAL("08", "공공임대"),
    PERMANENT_RENTAL("09", "영구임대"),
    HAPPY_HOUSE("10", "행복주택"),
    LONG_TERM_RENTAL("11", "장기전세"),
    NEWLY_BUILT_MULTI_HOUSEHOLD_BUY_TO_RENT("27", "신축다세대매입임대"),
    HOME_BASED_CHILDCARE_CENTER("40", "가정어린이집"),
    BUY_TO_RENT("26", "매입임대"),
    JEONSE_RENTAL("17", "전세임대"),
    LESSOR_BUILD_TO_RENT("36", "집주인임대(건설개량형)"),
    LESSOR_BUY_TO_RENT("37", "집주인임대(매입형)"),
    PUBILC_PRE_SALE("39", "공공분양"),
    NEWLYWEDS_HAPPY_HOUSE("42", "행복주택");

    public final String code;
    public final String name;

    AnnouncementDetailType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    static public AnnouncementDetailType codeOf(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findAny()
                .orElse(null);
    }
}
