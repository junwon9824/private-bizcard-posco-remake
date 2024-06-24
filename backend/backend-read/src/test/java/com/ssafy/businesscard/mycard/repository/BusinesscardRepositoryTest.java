//package com.ssafy.businesscard.mycard.repository;
//
//import com.ssafy.businesscard.mycard.dto.MycardResponseDto;
//import com.ssafy.businesscard.mycard.entity.Businesscard;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Replace;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 데이터베이스를 사용할 경우
//
//@ExtendWith(SpringExtension.class)
//public class BusinesscardRepositoryTest {
//
//    @Mock
//    private BusinesscardRepository businesscardRepository;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testFindByUser_UserId() {
//        Long userId = 1L;
//
//        MycardResponseDto frontCard = MycardResponseDto.builder()
//                .frontBack(Businesscard.Status.FRONT)
//                .build();
//        MycardResponseDto backCard = MycardResponseDto.builder()
//                .frontBack(Businesscard.Status.BACK)
//                .build();
//
//        List<MycardResponseDto> mockCardList = Arrays.asList(frontCard, backCard);
//
//        when(businesscardRepository.findByUser_UserId(userId)).thenReturn(mockCardList);
//
//        List<MycardResponseDto> result = businesscardRepository.findByUser_UserId(userId);
//
//        // 값 출력
//        System.out.println("Result size: " + result.size());
//        result.forEach(card -> System.out.println("Card Status: " + card.frontBack()));
//
//        assertEquals(2, result.size());
//        assertEquals(Businesscard.Status.FRONT, result.get(0).frontBack());
//        assertEquals(Businesscard.Status.BACK, result.get(1).frontBack());
//    }
//
//    @Test
//    public void testFindAllByNameContainingOrCompanyContaining() {
//        String name = "John";
//        String company = "ACME";
//
//        Businesscard card1 = Businesscard.builder()
//                .name("John Doe")
//                .company("ACME Corp")
//                .build();
//
//        Businesscard card2 = Businesscard.builder()
//                .name("Jane Smith")
//                .company("ACME Inc")
//                .build();
//
//        List<Businesscard> mockCardList = Arrays.asList(card1, card2);
//
//        when(businesscardRepository.findAllByNameContainingOrCompanyContaining(name, company)).thenReturn(mockCardList);
//
//        List<Businesscard> result = businesscardRepository.findAllByNameContainingOrCompanyContaining(name, company);
//
//        // 값 출력
//        System.out.println("Result size: " + result.size());
//        result.forEach(card -> System.out.println("Card Name: " + card.getName() + ", Company: " + card.getCompany()));
//
//        assertEquals(2, result.size());
//        assertEquals("John Doe", result.get(0).getName());
//        assertEquals("ACME Corp", result.get(0).getCompany());
//        assertEquals("Jane Smith", result.get(1).getName());
//        assertEquals("ACME Inc", result.get(1).getCompany());
//    }
//}
