package com.zip9.api.announcement.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.util.Assert;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDetailRequest {
    @NotNull
    @Parameter(description = "공고 ID", in = ParameterIn.PATH)
    private Long announcementId;

//    @Parameter(description = "타사 공고 ID", in = ParameterIn.QUERY, example = "2015122300014121")
//    private String thirdPartyAnnouncementId;
//
//    @EnumConstraints(enumClass = AnnouncementType.class, ignoreCase = true, nullable = false)
//    @Parameter(description = "공고유형", in = ParameterIn.QUERY)
//    @Schema(implementation = AnnouncementType.class)
//    private String announcementType;
//    @EnumConstraints(enumClass = AnnouncementDetailType.class, ignoreCase = true, nullable = false)
//    @Parameter(description = "공고상세유형", in = ParameterIn.QUERY)
//    @Schema(implementation = AnnouncementDetailType.class)
//    private String announcementDetailType;
//    @EnumConstraints(enumClass = HouseSupplyType.class, ignoreCase = true, nullable = false)
//    @Parameter(description = "공급유형", in = ParameterIn.QUERY)
//    @Schema(implementation = HouseSupplyType.class)
//    private String supplyType;
//    @Parameter(description = "고객센터유형코드", in = ParameterIn.QUERY, example = "03")
//    private String csTypeCode;

    @Builder
    public AnnouncementDetailRequest(AnnouncementDetailRequest request) {
        Assert.notNull(request.getAnnouncementId(), "'id' cannot be null.");
//        Assert.notNull(request.getAnnouncementType(), "'announcementType' cannot be null.");
//        Assert.notNull(request.getAnnouncementDetailType(), "'announcementDetailType' cannot be null.");
//        Assert.notNull(request.getSupplyType(), "'supplyType' cannot be null.");
//        Assert.notNull(request.getCsTypeCode(), "'csTypeCode' cannot be null.");
//        Assert.notNull(AnnouncementType.valueOf(request.getAnnouncementType()), "'announcementType' is invalid.");
//        Assert.notNull(AnnouncementDetailType.valueOf(request.getAnnouncementDetailType()), "'announcementDetailType' is invalid.");
//        Assert.notNull(HouseSupplyType.valueOf(request.getSupplyType()), "'supplyType' is invalid.");

        this.announcementId = request.getAnnouncementId();
//        this.announcementType = request.getAnnouncementType();
//        this.announcementDetailType = request.getAnnouncementDetailType();
//        this.supplyType = request.getSupplyType();
//        this.csTypeCode = request.getCsTypeCode();
    }
}
