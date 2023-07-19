package com.zip9.api.announcement.dto;

import com.zip9.api.LH.dto.LHAnnouncementRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.zip9.api.LH.enums.AnnouncementStatus.*;
import static com.zip9.api.LH.enums.AnnouncementType.*;

@Data
@Builder
public class AnnouncementsMigrationRequest {
    @NotNull
    private LocalDate from;
    private LocalDate to;

    public AnnouncementsMigrationRequest(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = ObjectUtils.isEmpty(to) ? from : to;
    }

    public LocalDateTime getFromDatetime() {
        return LocalDateTime.of(from, LocalTime.MIN);
    }

    public LocalDateTime getToDateTime() {
        return LocalDateTime.of(to, LocalTime.MAX);
    }

    public List<LHAnnouncementRequest> buildLHRequests() {
        List<LHAnnouncementRequest> requests = new ArrayList<>();
        List<String> announcementTypes = List.of(RENT.name(), WELFARE.name(), NEWLYWEDS.name());
        List<String> announcementStatus = List.of(ANNOUNCED.name(), ACCEPTABLE.name(), ANNOUNCED_CORRECTLY.name());

        for (String announcementType : announcementTypes) {
            for (String status : announcementStatus) {
                requests.add(LHAnnouncementRequest.builder()
                        .registStartDate(from)
                        .registEndDate(to)
                        .announcementType(announcementType)
                        .announcementStatus(status)
                        .build()
                );
            }
        }

        return requests;
    }
}
