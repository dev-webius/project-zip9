package com.zip9.api.announcement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class AnnouncementsCrawling {
    @NotNull
    private LocalDate from;
    private LocalDate to;

    public AnnouncementsCrawling(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = ObjectUtils.isEmpty(to) ? from : to;
    }

    public LocalDateTime getFromDatetime() {
        return LocalDateTime.of(from, LocalTime.MIN);
    }

    public LocalDateTime getToDateTime() {
        return LocalDateTime.of(to, LocalTime.MAX);
    }
}
