package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEtcEntity is a Querydsl query type for EtcEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEtcEntity extends EntityPathBase<EtcEntity> {

    private static final long serialVersionUID = 1757032694L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEtcEntity etcEntity = new QEtcEntity("etcEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final QAnnouncementEntity announcement;

    public final StringPath announcementDescription = createString("announcementDescription");

    public final StringPath caution = createString("caution");

    public final StringPath comment = createString("comment");

    public final StringPath correctOrCancelReason = createString("correctOrCancelReason");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath groupHomeAgency = createString("groupHomeAgency");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath leaseCondition = createString("leaseCondition");

    public final StringPath leaseTerms = createString("leaseTerms");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> numberOfSupplyHousehold = createNumber("numberOfSupplyHousehold", Integer.class);

    public final StringPath receptionAddress = createString("receptionAddress");

    public final NumberPath<Integer> supportLimitAmount = createNumber("supportLimitAmount", Integer.class);

    public final StringPath targetArea = createString("targetArea");

    public final StringPath targetHouse = createString("targetHouse");

    public QEtcEntity(String variable) {
        this(EtcEntity.class, forVariable(variable), INITS);
    }

    public QEtcEntity(Path<? extends EtcEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEtcEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEtcEntity(PathMetadata metadata, PathInits inits) {
        this(EtcEntity.class, metadata, inits);
    }

    public QEtcEntity(Class<? extends EtcEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.announcement = inits.isInitialized("announcement") ? new QAnnouncementEntity(forProperty("announcement")) : null;
    }

}

