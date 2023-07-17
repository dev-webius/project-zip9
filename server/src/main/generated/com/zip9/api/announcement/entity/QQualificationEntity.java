package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQualificationEntity is a Querydsl query type for QualificationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQualificationEntity extends EntityPathBase<QualificationEntity> {

    private static final long serialVersionUID = -1858459055L;

    public static final QQualificationEntity qualificationEntity = new QQualificationEntity("qualificationEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QQualificationEntity(String variable) {
        super(QualificationEntity.class, forVariable(variable));
    }

    public QQualificationEntity(Path<? extends QualificationEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQualificationEntity(PathMetadata metadata) {
        super(QualificationEntity.class, metadata);
    }

}

