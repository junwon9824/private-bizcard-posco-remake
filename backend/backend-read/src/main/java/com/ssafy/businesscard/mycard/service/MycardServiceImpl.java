package com.ssafy.businesscard.mycard.service;

import com.ssafy.businesscard.mycard.dto.MycardListResponseDto;
import com.ssafy.businesscard.mycard.dto.MycardResponseDto;
import com.ssafy.businesscard.mycard.dto.RecentlyAddCardList;
import com.ssafy.businesscard.mycard.entity.Businesscard;
import com.ssafy.businesscard.mycard.repository.BusinesscardRepository;
import com.ssafy.businesscard.privateAlbum.dto.PrivateAlbumResponseDto;
import com.ssafy.businesscard.privateAlbum.service.PrivateAlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MycardServiceImpl implements MycardService{

    private final BusinesscardRepository businesscardRepository;
    private final PrivateAlbumService privateAlbumService;

    //내 명함 전체조회
    @Override
    public List<MycardResponseDto> searchMycard(Long userId){
        return businesscardRepository.findByUser_UserId(userId);
    }

    //내 명함탭
    @Override
    public MycardListResponseDto getMycard(Long userId){
        long startTime = System.currentTimeMillis();

        List<MycardResponseDto> list = searchMycard(userId);
        MycardResponseDto front = null;
        MycardResponseDto back = null;
        for (MycardResponseDto u : list) {
            log.info("u.frontback확인 : {}",u.frontBack());
            if (u.frontBack() == Businesscard.Status.FRONT) {
                front = u;
            } else if (u.frontBack() == Businesscard.Status.BACK) {
                back = u;
            }
        }

        List<PrivateAlbumResponseDto> privateAlbumResponseDtos = privateAlbumService.getAlbumList(userId, 0);
        MycardListResponseDto mycardListResponseDto = new MycardListResponseDto(userId, front, back, privateAlbumResponseDtos);
        long endTime = System.currentTimeMillis();

        System.out.println("인덱스 추가 전 소요 시간: " + (endTime - startTime) + "ms");

        return mycardListResponseDto;
    }
}
