package com.zip9.api.announcement.service;

import com.zip9.api.announcement.dto.*;
import com.zip9.api.announcement.entity.*;
import com.zip9.api.announcement.repository.*;
import com.zip9.api.common.enums.Code;
import com.zip9.api.common.exception.GeneralException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementDBService implements AnnouncementReadService {
    private AnnouncementRepository announcementRepository;
    private HouseComplexRepository houseComplexRepository;
    private HouseComplexAttachmentRepository houseComplexAttachmentRepository;
    private SupplyScheduleRepository supplyScheduleRepository;
    private ReceptionRepository receptionRepository;
    private EtcRepository etcRepository;

    @Override
    public AnnouncementResponse getAnnouncements(AnnouncementRequest request) {
        List<Announcement> announcements = announcementRepository.findAnnouncements(request);

        // API Response 데이터 생성
        AnnouncementResponse response = new AnnouncementResponse();

        for (Announcement announcement : announcements) {
            Map<String, Integer> numberOfAnnouncementsByCity = response.getMeta().getNumberOfAnnouncementsByCity();

            // 도시별 공고 추가
            response.getItem().getAnnouncements().get(announcement.getCityShortName()).add(announcement);

            // 도시별 공고수 증가
            numberOfAnnouncementsByCity.put(
                    announcement.getCityShortName(),
                    numberOfAnnouncementsByCity.get(announcement.getCityShortName()) + 1
            );
        }

        return response;
    }

    @Override
    public AnnouncementDetailResponse getAnnouncementDetail(AnnouncementDetailRequest request) {
        AnnouncementEntity announcement = announcementRepository.findById(request.getAnnouncementId()).orElseThrow(() -> new GeneralException(Code.NOT_FOUND));
        ReceptionEntity receptionEntity = receptionRepository.findByAnnouncement(announcement);
        EtcEntity etcEntity = etcRepository.findByAnnouncement(announcement);
        List<SupplyScheduleEntity> supplyScheduleEntities = supplyScheduleRepository.findAllByAnnouncement(announcement);
        List<HouseComplexEntity> houseComplexEntities = houseComplexRepository.findAllByAnnouncement(announcement);

        AnnouncementDetailResponse.Reception reception = AnnouncementDetailResponse.Reception.buildFrom(receptionEntity);
        AnnouncementDetailResponse.Etc etc = AnnouncementDetailResponse.Etc.buildFrom(etcEntity);
        List<AnnouncementDetailResponse.SupplySchedule> supplySchedules = supplyScheduleEntities.stream().map(AnnouncementDetailResponse.SupplySchedule::buildFrom).toList();
        List<AnnouncementDetailResponse.HouseComplex> houseComplexes = houseComplexEntities.stream().map(AnnouncementDetailResponse.HouseComplex::buildFrom).toList();

        Map<String, AnnouncementDetailResponse.HouseComplex> houseComplexesMap = houseComplexes.stream()
                .collect(
                        Collectors.toMap(
                                AnnouncementDetailResponse.HouseComplex::getNameOrDetailAddress,
                                houseComplex -> houseComplex
                        )
                );

        return AnnouncementDetailResponse.builder()
                .supplySchedules(supplySchedules)
                .houseComplexes(AnnouncementDetailResponse.HouseComplexes.builder()
                        .names(houseComplexesMap.keySet().stream().toList())
                        .map(houseComplexesMap)
                        .build())
                .etc(etc)
                .reception(reception)
                .build()
        ;
    }

    /**
     * 공고 저장
     */
    @Transactional(readOnly = false)
    public AnnouncementEntity save(AnnouncementEntity entity) {
        return announcementRepository.save(entity);
    }

    /**
     * 공고 상세 - 주택단지 저장
     */
    @Transactional(readOnly = false)
    public HouseComplexEntity save(HouseComplexEntity entity) {
        return houseComplexRepository.save(entity);
    }

    /**
     * 공고 상세 - 주택단지별 첨부파일 저장
     */
    @Transactional(readOnly = false)
    public HouseComplexAttachmentEntity save(HouseComplexAttachmentEntity entity) {
        return houseComplexAttachmentRepository.save(entity);
    }

    /**
     * 공고 상세 - 공급일정 저장
     */
    @Transactional(readOnly = false)
    public SupplyScheduleEntity save(SupplyScheduleEntity entity) {
        return supplyScheduleRepository.save(entity);
    }

    /**
     * 공고 상세 - 접수처 저장
     */
    @Transactional(readOnly = false)
    public ReceptionEntity save(ReceptionEntity entity) {
        return receptionRepository.save(entity);
    }

    /**
     * 공고 상세 - 기타 저장
     */
    @Transactional(readOnly = false)
    public EtcEntity save(EtcEntity entity) {
        return etcRepository.save(entity);
    }
}
