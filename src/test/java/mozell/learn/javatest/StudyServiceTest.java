package mozell.learn.javatest;

import mozell.learn.mockitoPack.domain.Member;
import mozell.learn.mockitoPack.domain.Study;
import mozell.learn.mockitoPack.member.MemberService;
import mozell.learn.mockitoPack.study.StudyRepository;
import mozell.learn.mockitoPack.study.StudyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

    @Test
    @DisplayName("Mockito.mock() 메서드 생성")
    public void createStudyService_1() {
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }



    @Mock private MemberService memberService;
    @Mock private StudyRepository studyRepository;

    @Test
    @DisplayName("@Mock 생성")
    public void createStudyService_2() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }



    @Test
    @DisplayName("@Mock 매개변수 생성")
    public void createStudyService_3(@Mock MemberService memberService,
                                     @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }



    @Test
    @DisplayName("Mock 객체 Stubbing - thenReturn, 객체지정")
    public void createNewStudy_1(@Mock MemberService memberService,
                                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        // 테스트 member객체 생성
        Member member = new Member();
        member.setId(1L);
        member.setEmail("mozell@email.com");

        // '1L' 이라는 매개변수로 'findById' 가 호출되면, Optional<member> 객체를 리턴하라!
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        // test 할 'java study' 생성
        Study study = new Study(10, "java study");

        // 테스트 member 객체 정상 find 되는지 테스트
        Optional<Member> findMember = memberService.findById(1L);
        assertEquals("mozell@email.com", findMember.get().getEmail());

        studyService.createNewStudy(1L, study);
    }



    @Test
    @DisplayName("Mock 객체 Stubbing - thenReturn, ArgumentMatchers.any()")
    public void createNewStudy_2(@Mock MemberService memberService,
                                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        // 테스트 member객체 생성
        Member member = new Member();
        member.setEmail("mozell@email.com");

        // ArgumentMatchers.any() : 아무(any) 매개변수 받으면, 무조건 member 를 리턴한다.
        when(memberService.findById(any())).thenReturn(Optional.of(member));

        // 테스트 member 객체 any 처리 확인
        assertEquals("mozell@email.com", memberService.findById(1L).get().getEmail());
        assertEquals("mozell@email.com", memberService.findById(2L).get().getEmail());
    }



    @Test
    @DisplayName("Mock 객체 Stubbing - thenThrow")
    public void createNewStudy_3(@Mock MemberService memberService,
                                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        // 테스트 member객체 생성
        Member member = new Member();
        member.setId(1L);
        member.setEmail("mozell@email.com");

        // '1L' 이라는 매개변수로 'findById' 가 호출되면, RuntimeException 을 발생한다
        when(memberService.findById(1L)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            Optional<Member> findMember = memberService.findById(1L);
        });
        Optional<Member> findMember = memberService.findById(2L);
    }



    @Test
    @DisplayName("Mock 객체 Stubbing - doThrow")
    public void createNewStudy_4(@Mock MemberService memberService,
                                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        // 테스트 member객체 생성
        Member member = new Member();
        member.setId(1L);
        member.setEmail("mozell@email.com");

        // IllegalArgumentException 를 발생시켜라, '1L' 이라는 매개변수로 'validate' 가 호출되면,
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });
        memberService.validate(2L);
    }



    @Test
    @DisplayName("Mock 객체 Stubbing - 호출 순서에 다르게 Mocking")
    public void createNewStudy_5(@Mock MemberService memberService,
                                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        // 테스트 member객체 생성
        Member member = new Member();
        member.setId(1L);
        member.setEmail("mozell@email.com");

        // 순서에 따른 mocking
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))        // 1번째 호출 시 수행
                .thenThrow(new RuntimeException())      // 2번째 호출 시 수행
                .thenReturn(Optional.empty());          // 3번째 호출 시 수행

        Optional<Member> findMember = memberService.findById(1L);           // 1번째 호출
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);                                     // 2번째 호출
        });
        assertEquals(Optional.empty(), memberService.findById(3L)); // 3번째 호출
    }

    @Test
    @DisplayName("Mock 객체 Stubbing 연습문제")
    public void stubbingTest() {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

        Study study = new Study(10, "TEST");

        // [TO-DO] memberService 객체에 findById 메소드를 1L 값으로 호출하면, Optional.of(member) 객체를 리턴하도록 Stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        // [TO-DO] studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());

    }

    @Test
    @DisplayName("Mock 객체 확인")
    public void mockObjCheck() {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@email.com");

        Study study = new Study(10, "TEST");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        // [verify, times] 특정 메소드가 호출이 여부 체크(횟수까지 체크 가능)
        //notify() 메소드가 'study' 객체로 '1'번 호출되었는지 체크 -> 안되었으면 에러
        verify(memberService, times(1)).notify(study);
        //notify() 메소드가 'member' 객체로 '1'번 호출되었는지 체크 -> 안되었으면 에러
        verify(memberService, times(1)).notify(member);

        // [verify, never] validate() 메소드가 아무(any)객체로 호출되지 않았는지 체크
        verify(memberService, never()).validate(any());

        // [InOrder] 메소드 호출 순서 체크
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);    // 'study'  로 첫번째 호출 체크
        inOrder.verify(memberService).notify(member);   // 'member' 로 두번째 호출 체크

        // [verifyNoMoreInteractions] 더이상 mock 객체를 사용하지 않는지 체크
        verifyNoMoreInteractions(memberService);

    }
}
