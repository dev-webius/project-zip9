package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnnouncementEntity is a Querydsl query type for AnnouncementEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnnouncementEntity extends EntityPathBase<AnnouncementEntity> {

    private static final long serialVersionUID = -1771884405L;

    public static final QAnnouncementEntity announcementEntity = new QAnnouncementEntity("announcementEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final DateTimePath<java.time.LocalDateTime> announcedAt = createDateTime("announcedAt", java.time.LocalDateTime.class);

    public final StringPath cityCode = createString("cityCode");

    public final DateTimePath<java.time.LocalDateTime> closedAt = createDateTime("closedAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath csTypeCode = createString("csTypeCode");

    public final StringPath detailTypeCode = createString("detailTypeCode");

    public final StringPath detailUrl = createString("detailUrl");

    public final StringPath detailUrlMobile = createString("detailUrlMobile");

    public final ListPath<HouseComplexEntity, QHouseComplexEntity> houseComplexes = this.<HouseComplexEntity, QHouseComplexEntity>createList("houseComplexes", HouseComplexEntity.class, QHouseComplexEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final DateTimePath<java.time.LocalDateTime> registeredAt = createDateTime("registeredAt", java.time.LocalDateTime.class);

    public final StringPath statusCode = createString("statusCode");

    public final StringPath supplyTypeCode = createString("supplyTypeCode");

    public final StringPath thirdPartyId = createString("thirdPartyId");

    public final StringPath thirdPartyName = createString("thirdPartyName");

    public final StringPath title = createString("title");

    public final StringPath typeCode = createString("typeCode");

    public final BooleanPath used = createBoolean("used");

    public QAnnouncementEntity(String variable) {
        super(AnnouncementEntity.class, forVariable(variable));
    }

    public QAnnouncementEntity(Path<? extends AnnouncementEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnnouncementEntity(PathMetadata metadata) {
        super(AnnouncementEntity.class, metadata);
    }

}

