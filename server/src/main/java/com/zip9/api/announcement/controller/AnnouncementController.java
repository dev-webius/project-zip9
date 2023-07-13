package com.zip9.api.announcement.controller;

import com.zip9.api.announcement.dto.AnnouncementDetailRequest;
import com.zip9.api.announcement.dto.AnnouncementDetailsResponse;
import com.zip9.api.announcement.dto.AnnouncementRequest;
import com.zip9.api.announcement.dto.AnnouncementResponse;
import com.zip9.api.announcement.service.AnnouncementService;
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
    private final AnnouncementService announcementService;

    @Operation(summary = "공고 리스트 조회", description = "공고 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnnouncementResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Validation error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping
    public SuccessResponse<AnnouncementResponse> searchAnnouncements(@ParameterObject @Valid AnnouncementRequest announcementRequest) {
        return SuccessResponse.of(announcementService.searchAnnouncements(announcementRequest));
    }

    @Operation(summary = "공고 상세정보 조회", description = "공고 상세정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnnouncementDetailsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Validation error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Requested resource is not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/{announcementId}")
    public SuccessResponse<AnnouncementDetailsResponse> getAnnouncementDetail(@ParameterObject @Valid AnnouncementDetailRequest request) {
        return SuccessResponse.of(announcementService.getAnnouncementDetail(request));
    }
}

