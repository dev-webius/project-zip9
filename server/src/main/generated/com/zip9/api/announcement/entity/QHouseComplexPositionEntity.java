package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHouseComplexPositionEntity is a Querydsl query type for HouseComplexPositionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHouseComplexPositionEntity extends EntityPathBase<HouseComplexPositionEntity> {

    private static final long serialVersionUID = -831065219L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHouseComplexPositionEntity houseComplexPositionEntity = new QHouseComplexPositionEntity("houseComplexPositionEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QHouseComplexEntity houseComplex;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jibunAddress = createString("jibunAddress");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath roadAddress = createString("roadAddress");

    public final StringPath x = createString("x");

    public final StringPath y = createString("y");

    public QHouseComplexPositionEntity(String variable) {
        this(HouseComplexPositionEntity.class, forVariable(variable), INITS);
    }

    public QHouseComplexPositionEntity(Path<? extends HouseComplexPositionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHouseComplexPositionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHouseComplexPositionEntity(PathMetadata metadata, PathInits inits) {
        this(HouseComplexPositionEntity.class, metadata, inits);
    }

    public QHouseComplexPositionEntity(Class<? extends HouseComplexPositionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.houseComplex = inits.isInitialized("houseComplex") ? new QHouseComplexEntity(forProperty("houseComplex"), inits.get("houseComplex")) : null;
    }

}

