package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCityEntity is a Querydsl query type for CityEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCityEntity extends EntityPathBase<CityEntity> {

    private static final long serialVersionUID = -981867857L;

    public static final QCityEntity cityEntity = new QCityEntity("cityEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath englishName = createString("englishName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath shortName = createString("shortName");

    public QCityEntity(String variable) {
        super(CityEntity.class, forVariable(variable));
    }

    public QCityEntity(Path<? extends CityEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCityEntity(PathMetadata metadata) {
        super(CityEntity.class, metadata);
    }

}

