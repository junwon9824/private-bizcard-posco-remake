package com.ssafy.businesscard.mycard.service;

import com.ssafy.businesscard.mycard.dto.MycardListResponseDto;
import com.ssafy.businesscard.mycard.dto.MycardResponseDto;
import com.ssafy.businesscard.mycard.entity.Businesscard;
import com.ssafy.businesscard.mycard.repository.BusinesscardRepository;
import com.ssafy.businesscard.privateAlbum.dto.PrivateAlbumResponseDto;
import com.ssafy.businesscard.privateAlbum.service.PrivateAlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MycardServiceImplTest {

    @Mock
    private PrivateAlbumService privateAlbumService;

    @Mock
    private BusinesscardRepository businesscardRepository;

    @InjectMocks
    private MycardServiceImpl mycardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getmycard")
    public void testGetMycard() {
        Long userId = 1L;

        // Mock the response from businesscardRepository
        MycardResponseDto frontCard = MycardResponseDto.builder()
                .frontBack(Businesscard.Status.FRONT)
                .build();
        MycardResponseDto backCard = MycardResponseDto.builder()
                .frontBack(Businesscard.Status.BACK)
                .build();
        List<MycardResponseDto> mockCardList = Arrays.asList(frontCard, backCard);
        when(businesscardRepository.findByUser_UserId(userId)).thenReturn(mockCardList);

        // Mock the response from privateAlbumService
        PrivateAlbumResponseDto albumResponseDto = PrivateAlbumResponseDto.builder().build();
        List<PrivateAlbumResponseDto> mockAlbumList = Arrays.asList(albumResponseDto);
        when(privateAlbumService.getAlbumList(userId, 0)).thenReturn(mockAlbumList);

        // Call the method to test
        MycardListResponseDto result = mycardService.getMycard(userId);

        // Verify the result
        assertEquals(userId, result.getUserId());
        assertEquals(frontCard, result.getFront());
        assertEquals(backCard, result.getBack());
        assertEquals(mockAlbumList, result.getList());
    }
}
