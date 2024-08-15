package com.ssafy.businesscard.privateAlbum.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import com.ssafy.businesscard.privateAlbum.entity.PrivateAlbum;
import com.ssafy.businesscard.privateAlbum.repository.PrivateAlbumRepository;
import com.ssafy.businesscard.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // 임베디드 데이터베이스 사용
//@DataJpaTest
public class PrivateAlbumRepositoryIndexTest {

    @Autowired
    private PrivateAlbumRepository privateAlbumRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    @BeforeEach
    public void setUp() {
        // 사용자 생성 및 저장
        user = User.builder()
                .userId(1111L)
                .build();
        entityManager.persist(user);

        // 앨범 생성 및 저장
        for (int i = 0; i < 1000; i++) {
            PrivateAlbum album = PrivateAlbum.builder()
                    .user(user)
                    .build();
            entityManager.persist(album);
        }
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:remove_index.sql")
    public void testQueryPerformanceWithoutIndex() {
        Pageable pageable = PageRequest.of(0, 10);

        long startTime = System.nanoTime();
        Page<PrivateAlbum> page = privateAlbumRepository.findByUser_userId(user.getUserId(), pageable);
        long endTime = System.nanoTime();

        long durationWithoutIndex = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Duration without index: " + durationWithoutIndex + " ms");

        assertTrue(page.getNumberOfElements() > 0);
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:add_index.sql")
    public void testQueryPerformanceWithIndex() {
        Pageable pageable = PageRequest.of(0, 10);

        long startTime = System.nanoTime();
        Page<PrivateAlbum> page = privateAlbumRepository.findByUser_userId(user.getUserId(), pageable);
        long endTime = System.nanoTime();

        long durationWithIndex = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Duration with index: " + durationWithIndex + " ms");

        assertTrue(page.getNumberOfElements() > 0);
    }

}
