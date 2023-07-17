package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSupplyScheduleEntity is a Querydsl query type for SupplyScheduleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSupplyScheduleEntity extends EntityPathBase<SupplyScheduleEntity> {

    private static final long serialVersionUID = -892287894L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSupplyScheduleEntity supplyScheduleEntity = new QSupplyScheduleEntity("supplyScheduleEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final QAnnouncementEntity announcement;

    public final StringPath applicationDatetime = createString("applicationDatetime");

    public final StringPath applicationMethod = createString("applicationMethod");

    public final StringPath applicationTerm = createString("applicationTerm");

    public final StringPath contractTerm = createString("contractTerm");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath houseBrowseTerm = createString("houseBrowseTerm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath paperSubmitOpenAnnouncementDate = createString("paperSubmitOpenAnnouncementDate");

    public final StringPath paperSubmitTerm = createString("paperSubmitTerm");

    public final StringPath supplyScheduleGuide = createString("supplyScheduleGuide");

    public final StringPath target = createString("target");

    public final StringPath winnerAnnouncementDate = createString("winnerAnnouncementDate");

    public QSupplyScheduleEntity(String variable) {
        this(SupplyScheduleEntity.class, forVariable(variable), INITS);
    }

    public QSupplyScheduleEntity(Path<? extends SupplyScheduleEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSupplyScheduleEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSupplyScheduleEntity(PathMetadata metadata, PathInits inits) {
        this(SupplyScheduleEntity.class, metadata, inits);
    }

    public QSupplyScheduleEntity(Class<? extends SupplyScheduleEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.announcement = inits.isInitialized("announcement") ? new QAnnouncementEntity(forProperty("announcement")) : null;
    }

}

