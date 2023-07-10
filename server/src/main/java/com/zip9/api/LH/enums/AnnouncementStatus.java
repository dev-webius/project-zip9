package com.zip9.api.LH.enums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
@Schema(enumAsRef = true)
public enum AnnouncementStatus {
    ANNOUNCED("공고중", "공고중"),
    ACCEPTABLE("접수중", "접수중"),
//    CLOSED("접수마감", "접수마감"),
//    REQUEST_TO_CONTACT("상담요청", "상담요청"),
    ANNOUNCED_CORRECTLY("정정공고중", "정정공고중");

    public final String code;
    public final String name;
    AnnouncementStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    static public AnnouncementStatus codeOf(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findAny()
                .orElse(null);
    }
}
