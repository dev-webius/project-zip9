package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSupplyTypeEntity is a Querydsl query type for SupplyTypeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSupplyTypeEntity extends EntityPathBase<SupplyTypeEntity> {

    private static final long serialVersionUID = -583171379L;

    public static final QSupplyTypeEntity supplyTypeEntity = new QSupplyTypeEntity("supplyTypeEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public QSupplyTypeEntity(String variable) {
        super(SupplyTypeEntity.class, forVariable(variable));
    }

    public QSupplyTypeEntity(Path<? extends SupplyTypeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSupplyTypeEntity(PathMetadata metadata) {
        super(SupplyTypeEntity.class, metadata);
    }

}

