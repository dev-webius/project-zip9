package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHouseComplexEntity is a Querydsl query type for HouseComplexEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHouseComplexEntity extends EntityPathBase<HouseComplexEntity> {

    private static final long serialVersionUID = -965127692L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHouseComplexEntity houseComplexEntity = new QHouseComplexEntity("houseComplexEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final StringPath address = createString("address");

    public final QAnnouncementEntity announcement;

    public final StringPath appurtenantFacilities = createString("appurtenantFacilities");

    public final StringPath convenientFacilities = createString("convenientFacilities");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath detailAddress = createString("detailAddress");

    public final StringPath educationFacilities = createString("educationFacilities");

    public final StringPath expectedMoveInDate = createString("expectedMoveInDate");

    public final StringPath heatingTypeName = createString("heatingTypeName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath netLeasableAreaRange = createString("netLeasableAreaRange");

    public final StringPath supplyInfoGuide = createString("supplyInfoGuide");

    public final NumberPath<Integer> totalOfHousehold = createNumber("totalOfHousehold", Integer.class);

    public final StringPath trafficFacilities = createString("trafficFacilities");

    public QHouseComplexEntity(String variable) {
        this(HouseComplexEntity.class, forVariable(variable), INITS);
    }

    public QHouseComplexEntity(Path<? extends HouseComplexEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHouseComplexEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHouseComplexEntity(PathMetadata metadata, PathInits inits) {
        this(HouseComplexEntity.class, metadata, inits);
    }

    public QHouseComplexEntity(Class<? extends HouseComplexEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.announcement = inits.isInitialized("announcement") ? new QAnnouncementEntity(forProperty("announcement")) : null;
    }

}

