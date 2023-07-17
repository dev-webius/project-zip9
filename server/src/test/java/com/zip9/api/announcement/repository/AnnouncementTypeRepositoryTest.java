package com.zip9.api.announcement.repository;

import com.zip9.api.announcement.entity.AnnouncementTypeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnnouncementTypeRepositoryTest {
    @Autowired
    AnnouncementTypeRepository repository;

    @Test
    void DB_access_테스트() {
        List<AnnouncementTypeEntity> announcementTypeEntities = repository.findAll();
        System.out.println(announcementTypeEntities);


    }

}