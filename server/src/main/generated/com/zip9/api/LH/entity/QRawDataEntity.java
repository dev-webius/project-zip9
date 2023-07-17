package com.zip9.api.LH.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRawDataEntity is a Querydsl query type for RawDataEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRawDataEntity extends EntityPathBase<RawDataEntity> {

    private static final long serialVersionUID = -191444993L;

    public static final QRawDataEntity rawDataEntity = new QRawDataEntity("rawDataEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final StringPath announcement = createString("announcement");

    public final StringPath announcementDetail = createString("announcementDetail");

    public final StringPath announcementId = createString("announcementId");

    public final StringPath announcementSupply = createString("announcementSupply");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath status = createString("status");

    public QRawDataEntity(String variable) {
        super(RawDataEntity.class, forVariable(variable));
    }

    public QRawDataEntity(Path<? extends RawDataEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRawDataEntity(PathMetadata metadata) {
        super(RawDataEntity.class, metadata);
    }

}

