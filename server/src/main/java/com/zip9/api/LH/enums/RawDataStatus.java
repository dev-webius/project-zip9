package com.zip9.api.LH.enums;

import java.util.Arrays;

public enum RawDataStatus {
//    READY("READY", "준비"),
//    ANNOUNCEMENT_COMPLETED("ANNOUNCEMENT_COMPLETED", "공고 완료"),
//    ANNOUNCEMENT_DETAIL_COMPLETED("ANNOUNCEMENT_DETAIL_COMPLETED", "공고 상세정보 완료"),
//    ANNOUNCEMENT_SUPPLY_COMPLETED("ANNOUNCEMENT_SUPPLY_COMPLETED", "공고 공급정보 완료"),
    STEP_1_COMPLETED("STEP_1_COMPLETED", "OPEN API RAW DATA 저장 완료"),
    STEP_2_COMPLETED("STEP_2_COMPLETED", "RAW DATA 파싱 후 DB 저장 완료");

    public final String code;
    public final String name;

    RawDataStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RawDataStatus codeOf(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findAny()
                .orElse(null);
    }
}
