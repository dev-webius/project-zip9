package com.zip9.api.announcement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class AnnouncementRequest {
    @Parameter(description = "공고명", in = ParameterIn.QUERY)
    private String title;

//    @NotNull
//    @Parameter(description = "공고게시 시작일", in = ParameterIn.QUERY, example = "2023-07-01")
    @JsonIgnore
    private LocalDate registStartDate;

//    @NotNull
//    @Parameter(description = "공고게시 종료일", in = ParameterIn.QUERY, example = "2023-07-31")
    @JsonIgnore
    private LocalDate registEndDate;

//    @Parameter(description = "공고마감 시작일", in = ParameterIn.QUERY)
    @JsonIgnore
    private LocalDate closeStartDate;

//    @Parameter(description = "공고마감 종료일", in = ParameterIn.QUERY)
    @JsonIgnore
    private LocalDate closeEndDate;

//    @EnumConstraints(enumClass = City.class, ignoreCase = true, nullable = true)
//    @Parameter(description = "도시 (미입력 시 전체)", in = ParameterIn.QUERY)
//    @Schema(implementation = City.class)
    @JsonIgnore
    private String city;

//    @EnumConstraints(enumClass = AnnouncementType.class, ignoreCase = true, nullable = true)
//    @Parameter(description = "공고유형 (미입력 시 전체)", in = ParameterIn.QUERY)
//    @ArraySchema(schema = @Schema(implementation = AnnouncementType.class,
//            description ="RENT : 임대주택, WELFARE : 주거복지(매입임대), NEWLYWEDS : 신혼희망타운"))
    @JsonIgnore
    private List<String> announcementTypes;

//    @EnumConstraints(enumClass = AnnouncementStatus.class, ignoreCase = true, nullable = true)
//    @Parameter(description = "공고상태 (미입력 시 전체)")
//    @ArraySchema(schema = @Schema(implementation = AnnouncementStatus.class,
//            description ="ANNOUNCED : 공고중, ACCEPTABLE : 접수중, ANNOUNCED_CORRECTLY : 정정공고중"))
    @JsonIgnore
    private List<String> announcementStatus;

    @Builder
    public AnnouncementRequest(LocalDate registStartDate, LocalDate registEndDate, String title, List<String> announcementTypes, List<String> announcementStatus, String city, LocalDate closeStartDate, LocalDate closeEndDate) {
        this.title = title;

//        Assert.notNull(registStartDate, "시작일시가 입력되지 않았습니다.");
//        Assert.notNull(registEndDate, "종료일시가 입력되지 않았습니다.");
//        Assert.isTrue(!registStartDate.isAfter(registEndDate), "기간이 올바르지 않습니다.");

//        this.registStartDate = registStartDate;
//        this.registEndDate = registEndDate;
//        this.city = city;
//        this.closeStartDate = closeStartDate;
//        this.closeEndDate = closeEndDate;
//        this.announcementTypes = announcementTypes;
//        this.announcementStatus = announcementStatus;

//        if (ObjectUtils.isEmpty(announcementTypes)) {
//            this.announcementTypes = List.of(RENT.name(), WELFARE.name(), NEWLYWEDS.name());
//        } else {
//            this.announcementTypes = announcementTypes;
//        }

//        if (ObjectUtils.isEmpty(announcementStatus)) {
//            this.announcementStatus = List.of(ANNOUNCED.name(), ACCEPTABLE.name(), ANNOUNCED_CORRECTLY.name());
//        } else {
//            this.announcementStatus = announcementStatus;
//        }
    }
}
