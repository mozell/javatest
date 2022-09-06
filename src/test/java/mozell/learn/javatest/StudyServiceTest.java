package mozell.learn.javatest;

import mozell.learn.mockitoPack.member.MemberService;
import mozell.learn.mockitoPack.study.StudyRepository;
import mozell.learn.mockitoPack.study.StudyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

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
}
