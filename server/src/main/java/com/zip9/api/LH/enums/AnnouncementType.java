package com.zip9.api.LH.enums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
@Schema(enumAsRef = true)
public enum AnnouncementType {
//    LANDS("01", "토지"),
    PRESALES("05", "분양주택"),
    RENTAL("06", "임대주택"),
    WELFARE("13", "주거복지"),
//    COMMERCIAL("22", "상가"),
    NEWLYWEDS("39", "신혼희망타운");

    public final String code;
    public final String name;

    AnnouncementType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    static public AnnouncementType codeOf(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findAny()
                .orElse(null);
    }
}
