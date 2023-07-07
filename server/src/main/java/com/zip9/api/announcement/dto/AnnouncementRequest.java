package com.zip9.api.announcement.dto;

import com.zip9.api.LH.enums.AnnouncementStatus;
import com.zip9.api.LH.enums.AnnouncementType;
import com.zip9.api.LH.enums.City;
import com.zip9.api.common.contraints.EnumConstraints;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.zip9.api.LH.enums.AnnouncementStatus.*;
import static com.zip9.api.LH.enums.AnnouncementType.*;


@Data
public class AnnouncementRequest {
    @NotNull
    @Parameter(description = "공고게시 시작일", in = ParameterIn.QUERY, example = "2023-06-01")
    private LocalDate registStartDate;

    @NotNull
    @Parameter(description = "공고게시 종료일", in = ParameterIn.QUERY, example = "2023-12-31")
    private LocalDate registEndDate;

    @Parameter(description = "공고마감 시작일", in = ParameterIn.QUERY, example = "2023-06-01")
    private LocalDate closeStartDate;

    @Parameter(description = "공고마감 종료일", in = ParameterIn.QUERY, example = "2023-12-31")
    private LocalDate closeEndDate;

    @Parameter(description = "공고명", in = ParameterIn.QUERY)
    private String title;

    @EnumConstraints(enumClass = City.class, ignoreCase = true, nullable = true)
    @Parameter(description = "도시 (미입력 시 전체)", in = ParameterIn.QUERY, schema = @Schema(implementation = City.class))
    private String city;

    @EnumConstraints(enumClass = AnnouncementType.class, ignoreCase = true, nullable = true)
    @Parameter(description = "공고유형 (미입력 시 전체)", in = ParameterIn.QUERY)
    @ArraySchema(schema = @Schema(implementation = AnnouncementType.class))
    @Builder.Default
    private List<String> announcementTypes = new ArrayList<>();

    @EnumConstraints(enumClass = AnnouncementStatus.class, ignoreCase = true, nullable = true)
    @Parameter(description = "공고상태 (미입력 시 전체)")
    @ArraySchema(schema = @Schema(implementation = AnnouncementStatus.class))
    @Builder.Default
    private List<String> announcementStatus = new ArrayList<>();

//    @Min(value = 1)
//    @Parameter(description = "페이지", in = ParameterIn.QUERY, example = "1")
//    private Integer page;

//    @Builder.Default
//    @JsonIgnore
//    private Integer size = 10000;

    @Builder
    public AnnouncementRequest(LocalDate registStartDate, LocalDate registEndDate, String title, List<String> announcementTypes, List<String> announcementStatus, String city, LocalDate closeStartDate, LocalDate closeEndDate) {
        Assert.isTrue(registStartDate.isBefore(registEndDate), "기간이 올바르지 않습니다.");

        this.registStartDate = registStartDate;
        this.registEndDate = registEndDate;
        this.title = title;
        this.city = city;
        this.closeStartDate = closeStartDate;
        this.closeEndDate = closeEndDate;

        if (ObjectUtils.isEmpty(announcementTypes)) {
            this.announcementTypes = List.of(PRESALES.name(), RENTAL.name(), WELFARE.name(), NEWLYWEDS.name());
        } else {
            this.announcementTypes = announcementTypes;
        }

        if (ObjectUtils.isEmpty(announcementStatus)) {
            this.announcementStatus = List.of(ANNOUNCED.name(), ACCEPTABLE.name(), ANNOUNCED_CORRECTLY.name());
        } else {
            this.announcementStatus = announcementStatus;
        }
    }
}