package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReceptionEntity is a Querydsl query type for ReceptionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReceptionEntity extends EntityPathBase<ReceptionEntity> {

    private static final long serialVersionUID = 409938609L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReceptionEntity receptionEntity = new QReceptionEntity("receptionEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final StringPath address = createString("address");

    public final QAnnouncementEntity announcement;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath operationTerm = createString("operationTerm");

    public final StringPath receptionGuide = createString("receptionGuide");

    public final StringPath scheduleGuide = createString("scheduleGuide");

    public final StringPath telephoneNumber = createString("telephoneNumber");

    public QReceptionEntity(String variable) {
        this(ReceptionEntity.class, forVariable(variable), INITS);
    }

    public QReceptionEntity(Path<? extends ReceptionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReceptionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReceptionEntity(PathMetadata metadata, PathInits inits) {
        this(ReceptionEntity.class, metadata, inits);
    }

    public QReceptionEntity(Class<? extends ReceptionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.announcement = inits.isInitialized("announcement") ? new QAnnouncementEntity(forProperty("announcement")) : null;
    }

}

