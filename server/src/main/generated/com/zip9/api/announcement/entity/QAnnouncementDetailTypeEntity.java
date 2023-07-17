package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAnnouncementDetailTypeEntity is a Querydsl query type for AnnouncementDetailTypeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnnouncementDetailTypeEntity extends EntityPathBase<AnnouncementDetailTypeEntity> {

    private static final long serialVersionUID = -1753110122L;

    public static final QAnnouncementDetailTypeEntity announcementDetailTypeEntity = new QAnnouncementDetailTypeEntity("announcementDetailTypeEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath upperCode = createString("upperCode");

    public QAnnouncementDetailTypeEntity(String variable) {
        super(AnnouncementDetailTypeEntity.class, forVariable(variable));
    }

    public QAnnouncementDetailTypeEntity(Path<? extends AnnouncementDetailTypeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnnouncementDetailTypeEntity(PathMetadata metadata) {
        super(AnnouncementDetailTypeEntity.class, metadata);
    }

}

