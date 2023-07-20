package com.zip9.api.common.util;

import org.springframework.util.StringUtils;

public class BizStringUtlls {
    public static Boolean isEqualsIgnoringWhitespaces(String s1, String s2) {
        return s1.replaceAll("\\s", "").equals(s2.replaceAll("\\s", ""));
    }

    public static String makeTermValueFrom(String startDate, String endDate) {
        if (StringUtils.hasLength(startDate) && StringUtils.hasLength(endDate)) {
            return startDate + " ~ " + endDate;
        } else {
            return "";
        }
    }
}
