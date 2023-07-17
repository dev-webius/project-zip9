package com.zip9.api.announcement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAnnouncementStatusEntity is a Querydsl query type for AnnouncementStatusEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnnouncementStatusEntity extends EntityPathBase<AnnouncementStatusEntity> {

    private static final long serialVersionUID = -1320120035L;

    public static final QAnnouncementStatusEntity announcementStatusEntity = new QAnnouncementStatusEntity("announcementStatusEntity");

    public final com.zip9.api.common.entity.QBaseTimeEntity _super = new com.zip9.api.common.entity.QBaseTimeEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public QAnnouncementStatusEntity(String variable) {
        super(AnnouncementStatusEntity.class, forVariable(variable));
    }

    public QAnnouncementStatusEntity(Path<? extends AnnouncementStatusEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnnouncementStatusEntity(PathMetadata metadata) {
        super(AnnouncementStatusEntity.class, metadata);
    }

}

