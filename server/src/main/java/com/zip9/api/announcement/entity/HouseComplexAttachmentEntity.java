package com.zip9.api.announcement.entity;

import com.zip9.api.announcement.dto.AnnouncementDetailResponse;
import com.zip9.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Table(name = "TB_HOUSE_COMPLEX_ATTACHMENT",  indexes = {
        @Index(name = "IDX_ANNOUNCEMENT_01", columnList = "CREATED_AT", unique = false),
        @Index(name = "IDX_ANNOUNCEMENT_02", columnList = "MODIFIED_AT", unique = false),
})
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseComplexAttachmentEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOUSE_COMPLEX_ATTACHMENT_ID", nullable = false)
    private Long id;
    @Column(name = "FILE_NAME", length = 100)
    @Comment("파일명")
    private String fileName;
    @Column(name = "FILE_TYPE_NAME", length = 100)
    @Comment("파일타입명")
    private String fileTypeName;
    @Column(name = "DOWNLOAD_URL", length = 255)
    @Comment("다운로드 URL")
    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "HOUSE_COMPLEX_ID")
    @Comment("단지 ID")
    private HouseComplexEntity houseComplex;

    @Builder(builderMethodName = "ByAnnouncementDetailAttachmentBuilder")
    public HouseComplexAttachmentEntity(AnnouncementDetailResponse.Attachment attachment, HouseComplexEntity houseComplex) {
        this.fileName = attachment.getFileName();
        this.fileTypeName = attachment.getFileTypeName();
        this.downloadUrl = attachment.getDownloadUrl();
        this.houseComplex = houseComplex;
    }
}
