package com.zip9.api.LH.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zip9.api.LH.dto.LHAnnouncementDetailResponse;
import com.zip9.api.LH.dto.LHAnnouncementResponse;
import com.zip9.api.LH.dto.LHAnnouncementSupplyInfoResponse;
import com.zip9.api.LH.enums.RawDataStatus;
import com.zip9.api.common.contraints.EnumConstraints;
import com.zip9.api.common.entity.BaseTimeEntity;
import com.zip9.api.common.exception.GeneralException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Table(name = "TB_RAW_DATA", indexes = {
        @Index(name = "IDX_ANNOUNCEMENT_01", columnList = "CREATED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_02", columnList = "MODIFIED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_03", columnList = "THIRD_PARTY_ANNOUNCEMENT_ID", unique = false),
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RawDataEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RAW_DATA_ID")
    private Long id;

    @Column(name = "THIRD_PARTY_ANNOUNCEMENT_ID", nullable = false, length = 20)
    private String announcementId;

    @Column(name = "ANNOUNCEMENT", nullable = true, columnDefinition = "TEXT")
    private String announcement;

    @Column(name = "ANNOUNCEMENT_DETAIL", nullable = true, columnDefinition = "TEXT")
    private String announcementDetail;

    @Column(name = "ANNOUNCEMENT_SUPPLY", nullable = true, columnDefinition = "TEXT")
    private String announcementSupply;

    @EnumConstraints(enumClass = RawDataStatus.class)
    @Column(name = "STATUS", nullable = false)
    private String status;

    @Builder(builderMethodName = "ByLHAnnouncementBuilder", builderClassName = "ByLHAnnouncementBuilder")
    public RawDataEntity(String announcementId, LHAnnouncementResponse lhAnnouncement, LHAnnouncementDetailResponse lhAnnouncementDetail, LHAnnouncementSupplyInfoResponse lhSupplyInfo, String status) {
        Assert.notNull(announcementId, "'announcementId' cannot be null.");
        Assert.notNull(lhAnnouncement, "'lhAnnouncement' cannot be null.");
        Assert.notNull(status, "'status' cannot be null.");
        Assert.notNull(RawDataStatus.codeOf(status), "'status' is invalid.");

        this.announcementId = announcementId;
        this.announcement = objectToJson(lhAnnouncement);
        this.announcementDetail = objectToJson(lhAnnouncementDetail);
        this.announcementSupply = objectToJson(lhSupplyInfo);
        this.status = RawDataStatus.codeOf(status).code;
    }

    private String objectToJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new GeneralException(e);
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
