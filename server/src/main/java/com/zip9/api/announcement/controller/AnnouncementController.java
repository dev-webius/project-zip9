package com.zip9.api.announcement.controller;

import com.zip9.api.LH.service.ScheduleService;
import com.zip9.api.announcement.dto.*;
import com.zip9.api.announcement.service.AnnouncementDBService;
import com.zip9.api.common.dto.ErrorResponse;
import com.zip9.api.common.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공고", description = "공고 API")
@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementDBService service;
    private final ScheduleService scheduleService;

    @Operation(summary = "공고 리스트 조회", description = "공고 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnnouncementsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Validation error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping
    public SuccessResponse<AnnouncementsResponse, Meta> searchAnnouncements(@ParameterObject @Valid AnnouncementRequest announcementRequest) {
        AnnouncementsResponse announcements = service.getAnnouncements(announcementRequest);
        return SuccessResponse.of(announcements, announcements.getMeta());
    }

    @Operation(summary = "공고 상세정보 조회", description = "공고 상세정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnnouncementDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Validation error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Requested resource is not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/{announcementId}")
    public SuccessResponse<AnnouncementDetailResponse, ?> getAnnouncementDetail(@ParameterObject @Valid AnnouncementDetailRequest request) {
        return SuccessResponse.of(service.getAnnouncementDetail(request));
    }

    @Operation(summary = "공고 데이터 크롤링 스케쥴러 실행", description = "공고 데이터 크롤링 스케쥴러 실행")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnnouncementDetailResponse.class))),
    })
    @GetMapping("/scheduler")
    public SuccessResponse<Boolean> runScheduler(@ParameterObject @Valid AnnouncementsCrawling request) {
        return SuccessResponse.of(scheduleService.migration(request));

    }
}

