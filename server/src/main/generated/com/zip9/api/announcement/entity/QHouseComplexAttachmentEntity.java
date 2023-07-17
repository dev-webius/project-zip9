package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHouseComplexAttachmentEntity is a Querydsl query type for HouseComplexAttachmentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHouseComplexAttachmentEntity extends EntityPathBase<HouseComplexAttachmentEntity> {

    private static final long serialVersionUID = 1575440311L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHouseComplexAttachmentEntity houseComplexAttachmentEntity = new QHouseComplexAttachmentEntity("houseComplexAttachmentEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath downloadUrl = createString("downloadUrl");

    public final StringPath fileName = createString("fileName");

    public final StringPath fileTypeName = createString("fileTypeName");

    public final QHouseComplexEntity houseComplex;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QHouseComplexAttachmentEntity(String variable) {
        this(HouseComplexAttachmentEntity.class, forVariable(variable), INITS);
    }

    public QHouseComplexAttachmentEntity(Path<? extends HouseComplexAttachmentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHouseComplexAttachmentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHouseComplexAttachmentEntity(PathMetadata metadata, PathInits inits) {
        this(HouseComplexAttachmentEntity.class, metadata, inits);
    }

    public QHouseComplexAttachmentEntity(Class<? extends HouseComplexAttachmentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.houseComplex = inits.isInitialized("houseComplex") ? new QHouseComplexEntity(forProperty("houseComplex"), inits.get("houseComplex")) : null;
    }

}

