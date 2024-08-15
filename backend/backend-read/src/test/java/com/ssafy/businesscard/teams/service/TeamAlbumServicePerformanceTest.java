package com.ssafy.businesscard.teams.service;

import com.ssafy.businesscard.mycard.entity.Businesscard;
import com.ssafy.businesscard.mycard.repository.BusinesscardRepository;
import com.ssafy.businesscard.privateAlbum.dto.PrivateAlbumResponseDto;
import com.ssafy.businesscard.privateAlbum.entity.PrivateAlbum;
import com.ssafy.businesscard.privateAlbum.repository.PrivateAlbumRepository;
import com.ssafy.businesscard.teams.entity.TeamAlbum;
import com.ssafy.businesscard.teams.entity.TeamAlbumDetail;
import com.ssafy.businesscard.teams.entity.TeamAlbumMember;
import com.ssafy.businesscard.teams.repository.TeamAlbumDetailRepository;
import com.ssafy.businesscard.teams.repository.TeamAlbumRepository;
import com.ssafy.businesscard.user.entity.User;
import com.ssafy.businesscard.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@Slf4j
//@Transactional
public class TeamAlbumServicePerformanceTest {

    @Autowired
    private TeamsServiceImpl teamAlbumService;

    @Autowired
    private PrivateAlbumRepository privateAlbumRepository;

    @Autowired
    private TeamAlbumRepository teamAlbumRepository;

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private TeamAlbumDetailRepository teamAlbumDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinesscardRepository businesscardRepository;

//    @BeforeEach
//    public void setUp() {
//        // 테스트 데이터 삽입
//        List<TeamAlbumDetail> teamAlbumDetails = new ArrayList<>();
//
//        User user = User.builder().
//                userId(100L).
//                email("sd@naver.com").name("hong100").
//                build();
//        userRepository.save(user);
//
//        TeamAlbum teamAlbum = TeamAlbum.builder()
//                .teamAlbumId(2L)
//                .teamName("s").user(user)
//                .build();
//        TeamAlbum teamAlbum1 = teamAlbumRepository.save(teamAlbum);
//
//        for (int i = 0; i < 2; i++) {
//
//            User user1 = User.builder()
//                    .userId((long) i) // 각 사용자에 대해 고유한 ID 부여
//                    .email("user" + i + "@naver.com") // 각 사용자에 대해 고유한 이메일
//                    .name("User " + i) // 각 사용자에 대해 고유한 이름
//                    .build();
//            userRepository.save(user1);
//
//            // 비즈니스 카드 생성
//            Businesscard businesscard = Businesscard.builder()
//                    .email("business" + i + "@naver.com") // 각 카드에 대해 고유한 이메일
//                    .address("Address " + i) // 각 카드에 대해 고유한 주소
//                    .cardId((long) i) // 각 카드에 대해 고유한 카드 ID
//                    .company("Company " + i) // 각 카드에 대해 고유한 회사명
//                    .user(user1) // 사용자와 연관
//                    .frontBack(Businesscard.Status.FRONT)
//                    .name("sdf")
//                    .build();
//            businesscardRepository.save(businesscard);
//
////            PrivateAlbum album1 = new PrivateAlbum();
////            album1.setUser(user1);
////            album1.setMemo("Test Album 1");
////            // 필요한 필드 설정
////            privateAlbumRepository.save(album1); // 이 부분 추가
//
//            TeamAlbumDetail teamAlbumDetail = TeamAlbumDetail.builder()
//                    .teamAlbumDetailId((long) i).teamAlbum(teamAlbum).
//                    businesscard(businesscard)
//                    .build();
//
//            teamAlbumDetailRepository.save(teamAlbumDetail);
//
//            teamAlbumDetails.add(teamAlbumDetail);
//        }
//
//        TeamAlbum savedTeamAlbum = teamAlbumRepository.findAll().get(0);
//
//        System.out.println("tttt" + savedTeamAlbum.getTeamAlbumId());
//
//        savedTeamAlbum.setTeamAlbumDetail(teamAlbumDetails);
//        System.out.println("tttt" + savedTeamAlbum.getTeamAlbumId());
//
////        System.out.println("tttt"+teamAlbum1.getTeamAlbumDetail().get(0).getTeamAlbumDetailId());
//
//        teamAlbumRepository.save(savedTeamAlbum);
//    }

    @Test
    public void testPerformance() {
        long startTime, endTime;

        // 캐시 미사용
        startTime = System.currentTimeMillis();
        List<PrivateAlbumResponseDto> noCacheResult = teamAlbumService.getTeamAlbumListNoCache(3L, 0); // 캐시 미사용
        endTime = System.currentTimeMillis();
        System.out.println("No Cache Response Time: " + (endTime - startTime) + " ms" + noCacheResult.size()+noCacheResult);

        // 캐시 사용
        startTime = System.currentTimeMillis();
        Cache cache = cacheManager.getCache("teamAlbumCache");
        String cacheKey = 3 + "-" + 0;

        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(cacheKey);
            if (valueWrapper != null) {
                System.out.println("Cache hit for key: "+ cacheKey);
                // 캐시에서 데이터를 사용할 수 있음
                Object cachedValue = valueWrapper.get();
                // 처리 로직...
            } else {
                 System.out.println("Cache miss for key: {}"+ cacheKey);
                // 캐시에서 데이터가 없음
            }
        }
         System.out.println( "{ teamAlbumService.getTeamAlbumList(3L, 0); }"+teamAlbumService.getTeamAlbumList(3L, 0) );
        List<PrivateAlbumResponseDto> withCacheResult = teamAlbumService.getTeamAlbumList(3L, 0); // 캐시 사용

        endTime = System.currentTimeMillis();
        System.out.println("With Cache Response Time: " + (endTime - startTime) + " ms" + withCacheResult.size()+withCacheResult);

//
//        startTime = System.currentTimeMillis();
//        List<PrivateAlbumResponseDto> privateAlbumResponseDtos = teamAlbumService.getTeamAlbumAllList(2L); // 캐시 사용
//        endTime = System.currentTimeMillis();
//        System.out.println("privateAlbumResponseDtos Time: " + (endTime - startTime) + " ms" + privateAlbumResponseDtos.size());

        assertEquals(noCacheResult, withCacheResult); // 두 호출의 결과가 동일해야 함
    }

}
