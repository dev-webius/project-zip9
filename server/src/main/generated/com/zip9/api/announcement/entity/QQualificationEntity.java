package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQualificationEntity is a Querydsl query type for QualificationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQualificationEntity extends EntityPathBase<QualificationEntity> {

    private static final long serialVersionUID = -1858459055L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQualificationEntity qualificationEntity = new QQualificationEntity("qualificationEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final QAnnouncementEntity announcement;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath qualificationTypeName = createString("qualificationTypeName");

    public final StringPath requirement = createString("requirement");

    public QQualificationEntity(String variable) {
        this(QualificationEntity.class, forVariable(variable), INITS);
    }

    public QQualificationEntity(Path<? extends QualificationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQualificationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQualificationEntity(PathMetadata metadata, PathInits inits) {
        this(QualificationEntity.class, metadata, inits);
    }

    public QQualificationEntity(Class<? extends QualificationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.announcement = inits.isInitialized("announcement") ? new QAnnouncementEntity(forProperty("announcement")) : null;
    }

}

