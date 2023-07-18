package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHouseTypeEntity is a Querydsl query type for HouseTypeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHouseTypeEntity extends EntityPathBase<HouseTypeEntity> {

    private static final long serialVersionUID = 2055379484L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHouseTypeEntity houseTypeEntity = new QHouseTypeEntity("houseTypeEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final StringPath amount = createString("amount");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QHouseComplexEntity houseComplex;

    public final StringPath houseTypeName = createString("houseTypeName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath netLeasableArea = createString("netLeasableArea");

    public final NumberPath<Integer> numberOfApplicants = createNumber("numberOfApplicants", Integer.class);

    public final NumberPath<Integer> numberOfCandidates = createNumber("numberOfCandidates", Integer.class);

    public final NumberPath<Integer> numberOfHousehold = createNumber("numberOfHousehold", Integer.class);

    public final NumberPath<Integer> numberOfSupplyHousehold = createNumber("numberOfSupplyHousehold", Integer.class);

    public final StringPath rentFee = createString("rentFee");

    public final StringPath rentFeeEtc = createString("rentFeeEtc");

    public final StringPath supplyArea = createString("supplyArea");

    public QHouseTypeEntity(String variable) {
        this(HouseTypeEntity.class, forVariable(variable), INITS);
    }

    public QHouseTypeEntity(Path<? extends HouseTypeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHouseTypeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHouseTypeEntity(PathMetadata metadata, PathInits inits) {
        this(HouseTypeEntity.class, metadata, inits);
    }

    public QHouseTypeEntity(Class<? extends HouseTypeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.houseComplex = inits.isInitialized("houseComplex") ? new QHouseComplexEntity(forProperty("houseComplex"), inits.get("houseComplex")) : null;
    }

}

