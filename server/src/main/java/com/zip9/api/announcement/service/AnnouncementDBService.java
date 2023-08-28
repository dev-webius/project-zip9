package com.zip9.api.announcement.service;

import com.zip9.api.LH.enums.AnnouncementDetailType;
import com.zip9.api.LH.enums.AnnouncementStatus;
import com.zip9.api.LH.enums.City;
import com.zip9.api.announcement.dto.*;
import com.zip9.api.announcement.entity.*;
import com.zip9.api.announcement.repository.*;
import com.zip9.api.common.enums.Code;
import com.zip9.api.common.exception.GeneralException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementDBService implements AnnouncementReadService {
    private AnnouncementRepository announcementRepository;
    private HouseComplexRepository houseComplexRepository;
    private HouseComplexPositionRepository houseComplexPositionRepository;
    private HouseTypeRepository houseTypeRepository;
    private HouseComplexAttachmentRepository houseComplexAttachmentRepository;
    private SupplyScheduleRepository supplyScheduleRepository;
    private ReceptionRepository receptionRepository;
    private EtcRepository etcRepository;
    private QualificationRepository qualificationRepository;

    /**
     * 공고 목록 조회
     */
    @Override
    public AnnouncementsResponse getAnnouncements(AnnouncementRequest request) {
        // 공고 목록 조회
        List<AnnouncementEntity> announcements = announcementRepository.findAnnouncements(request);

        // 공고별 단지정보 조회
        List<HouseComplexEntity> houseComplexEntities = houseComplexRepository.findAllByAnnouncements(announcements);
        Map<Long, List<HouseComplexEntity>> houseComplexesMap = houseComplexEntities.stream()
                .collect(Collectors.groupingBy(houseComplex -> houseComplex.getAnnouncement().getId()));

        // API Response 데이터 생성
        AnnouncementsResponse response = new AnnouncementsResponse();

        for (AnnouncementEntity announcement : announcements) {
            List<HouseComplexEntity> houseComplexes = houseComplexesMap.get(announcement.getId());

            List<Announcement.Position> positions = new ArrayList<>();

            // 주택단지가 존재하지 않는 공고도 있음
            if (houseComplexes != null) {
                for (HouseComplexEntity houseComplex : houseComplexes) {
                    HouseComplexPositionEntity houseComplexPosition = houseComplex.getHouseComplexPositionEntity();
                    positions.add(Announcement.Position.buildFrom(houseComplexPosition));
                }
            }

            response.addAnnouncement(Announcement.builder()
                    .id(announcement.getId())
                    .title(announcement.getTitle())
                    .detailTypeName(AnnouncementDetailType.codeOf(announcement.getDetailTypeCode()).name)
                    .announcedDate(announcement.getAnnouncedAt().toLocalDate())
                    .closedDate(announcement.getClosedAt().toLocalDate())
                    .cityName(City.codeOf(announcement.getCityCode()).name)
                    .positions(positions)
                    .build()
            );
        }

        response.setTotal(announcementRepository.countAnnouncements(request));

        return response;
    }

    /**
     * 공고 상세정보 조회
     */
    @Override
    public AnnouncementDetailResponse getAnnouncementDetail(AnnouncementDetailRequest request) {
        AnnouncementEntity announcementEntity = announcementRepository.findById(request.getAnnouncementId()).orElseThrow(() -> new GeneralException(Code.NOT_FOUND));

        ReceptionEntity receptionEntity = receptionRepository.findByAnnouncement(announcementEntity);
        EtcEntity etcEntity = etcRepository.findByAnnouncement(announcementEntity);
        List<QualificationEntity> qualificationEntities = qualificationRepository.findAllByAnnouncement(announcementEntity);
        List<SupplyScheduleEntity> supplyScheduleEntities = supplyScheduleRepository.findAllByAnnouncement(announcementEntity);
        List<HouseComplexEntity> houseComplexEntities = houseComplexRepository.findAllByAnnouncement(announcementEntity);

        AnnouncementDetailResponse.Reception reception = AnnouncementDetailResponse.Reception.buildFrom(receptionEntity);
        AnnouncementDetailResponse.Etc etc = AnnouncementDetailResponse.Etc.buildFrom(etcEntity);
        List<AnnouncementDetailResponse.Qualification> qualifications = qualificationEntities.stream().map(AnnouncementDetailResponse.Qualification::buildFrom).toList();
        List<AnnouncementDetailResponse.SupplySchedule> supplySchedules = supplyScheduleEntities.stream().map(AnnouncementDetailResponse.SupplySchedule::buildFrom).toList();
        List<AnnouncementDetailResponse.HouseComplex> houseComplexes = houseComplexEntities.stream().map(AnnouncementDetailResponse.HouseComplex::buildFrom).toList();

        return AnnouncementDetailResponse.builder()
                .supplySchedules(supplySchedules)
                .houseComplexes(houseComplexes)
                .etc(etc)
                .reception(reception)
                .qualifications(qualifications)
                .build();
    }

    /**
     * 공고 저장
     */
    @Transactional
    public AnnouncementEntity save(AnnouncementEntity entity) {
        return announcementRepository.save(entity);
    }

    /**
     * 공고 상세 - 주택단지 저장
     */
    @Transactional
    public HouseComplexEntity save(HouseComplexEntity entity) {
        return houseComplexRepository.save(entity);
    }

    /**
     * 공고 상세 - 주택단지 위치정보 저장
     */
    @Transactional
    public HouseComplexPositionEntity save(HouseComplexPositionEntity entity) {
        return houseComplexPositionRepository.save(entity);
    }

    /**
     * 공고 상세 - 주택단지별 주택타입 저장
     */
    @Transactional
    public HouseTypeEntity save(HouseTypeEntity entity) {
        return houseTypeRepository.save(entity);
    }

    /**
     * 공고 상세 - 주택단지별 첨부파일 저장
     */
    @Transactional
    public HouseComplexAttachmentEntity save(HouseComplexAttachmentEntity entity) {
        return houseComplexAttachmentRepository.save(entity);
    }

    /**
     * 공고 상세 - 공급일정 저장
     */
    @Transactional
    public SupplyScheduleEntity save(SupplyScheduleEntity entity) {
        return supplyScheduleRepository.save(entity);
    }

    /**
     * 공고 상세 - 접수처 저장
     */
    @Transactional
    public ReceptionEntity save(ReceptionEntity entity) {
        return receptionRepository.save(entity);
    }

    /**
     * 공고 상세 - 기타 저장
     */
    @Transactional
    public EtcEntity save(EtcEntity entity) {
        return etcRepository.save(entity);
    }

    /**
     * 공고 상세 - 신청자격 저장
     */
    @Transactional
    public QualificationEntity save(QualificationEntity entity) {
        return qualificationRepository.save(entity);
    }

    /**
     * 마감 대상 공고 리스트 조회
     */
    @Transactional(readOnly = true)
    public List<AnnouncementEntity> getNotClosedAnnouncements(LocalDateTime closedAt) {
        return announcementRepository.findAllByStatusCodeNotAndClosedAtBefore(AnnouncementStatus.CLOSED.name(), closedAt);
    }

}
