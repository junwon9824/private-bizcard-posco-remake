package com.ssafy.businesscard.privateAlbum.repository;

import com.ssafy.businesscard.privateAlbum.entity.PrivateAlbum;
import com.ssafy.businesscard.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.IntStream;

@DataJpaTest
class PrivateAlbumRepositoryPerformanceTest {

    @Autowired
    private PrivateAlbumRepository privateAlbumRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        // 테스트 데이터를 준비합니다.
        User user = User.builder()
                .userId(1L)
                .build();

        IntStream.range(0, 1000).forEach(i -> {
            PrivateAlbum album = PrivateAlbum.builder()
                    .user(user)
                    .build();
            privateAlbumRepository.save(album);
        });

        // 인덱스를 삭제합니다.
        entityManager.getTransaction().begin();
        Query dropIndexQuery = entityManager.createNativeQuery("DROP INDEX IF EXISTS idx_user_name");
        dropIndexQuery.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    void testFindByUser_userIdWithoutIndex() {
        Pageable pageable = PageRequest.of(0, 10);

        long startTime = System.nanoTime();
        privateAlbumRepository.findByUser_userId(1L, pageable);
        long endTime = System.nanoTime();

        long durationWithoutIndex = endTime - startTime;
        System.out.println("Duration without index: " + durationWithoutIndex + " nanoseconds");

        // 인덱스를 추가합니다.
        entityManager.getTransaction().begin();
        Query createIndexQuery = entityManager.createNativeQuery("CREATE INDEX idx_user_name ON private_album (user_id)");
        createIndexQuery.executeUpdate();
        entityManager.getTransaction().commit();

        startTime = System.nanoTime();
        privateAlbumRepository.findByUser_userId(1L, pageable);
        endTime = System.nanoTime();

        long durationWithIndex = endTime - startTime;
        System.out.println("Duration with index: " + durationWithIndex + " nanoseconds");
    }
}
