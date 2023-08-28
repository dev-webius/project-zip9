package com.zip9.api.announcement.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zip9.api.announcement.dto.AnnouncementRequest;
import com.zip9.api.announcement.entity.AnnouncementEntity;
import com.zip9.api.announcement.entity.QAnnouncementEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnnouncementCustomRepositoryImpl implements AnnouncementCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QAnnouncementEntity qAnnouncement = QAnnouncementEntity.announcementEntity;

    @Override
    public List<AnnouncementEntity> findAnnouncements(AnnouncementRequest request) {
        return jpaQueryFactory.select(qAnnouncement)
                .from(qAnnouncement)
                .where(ByRequestBooleanBuilder(request))
                .orderBy(qAnnouncement.announcedAt.desc())
                .fetch();
    }

    @Override
    public Long countAnnouncements(AnnouncementRequest request) {
        return jpaQueryFactory
                .select(qAnnouncement.count())
                .from(qAnnouncement)
                .where(ByRequestBooleanBuilder(request))
                .fetchOne();
    }

    private BooleanBuilder ByRequestBooleanBuilder(AnnouncementRequest request) {
        BooleanBuilder builder = new BooleanBuilder();

        if (ObjectUtils.isNotEmpty(request.getRegistStartDate())) {
            builder.and(qAnnouncement.announcedAt.goe(LocalDateTime.of(request.getRegistStartDate(), LocalTime.MIN)));
        }

        if (ObjectUtils.isNotEmpty(request.getRegistEndDate())) {
            builder.and(qAnnouncement.announcedAt.loe(LocalDateTime.of(request.getRegistEndDate(), LocalTime.MAX)));
        }

        if (ObjectUtils.isNotEmpty(request.getCloseStartDate())) {
            builder.and(qAnnouncement.closedAt.goe(LocalDateTime.of(request.getCloseStartDate(), LocalTime.MIN)));
        }

        if (ObjectUtils.isNotEmpty(request.getCloseEndDate())) {
            builder.and(qAnnouncement.closedAt.loe(LocalDateTime.of(request.getCloseEndDate(), LocalTime.MAX)));
        }

        if (StringUtils.hasLength(request.getTitle())) {
            builder.and(qAnnouncement.title.contains(request.getTitle()));
        }

//        if (StringUtils.hasLength(request.getCity())) {
//            builder.and(qAnnouncement.cityCode.eq(request.getCityCode()));
//        }
//
//        if (!request.getAnnouncementTypes().isEmpty()) {
//            builder.and(qAnnouncement.typeCode.in(request.getAnnounceTypesCodes()));
//        }
//
//        if (!request.getAnnouncementStatus().isEmpty()) {
//            builder.and(qAnnouncement.statusCode.in(request.getAnnounceStatusCodes()));
//        }

        return builder;
    }
}
