package mozell.learn.javatest;

import mozell.learn.mockitoPack.domain.Member;
import mozell.learn.mockitoPack.domain.Study;
import mozell.learn.mockitoPack.member.MemberNotFoundException;
import mozell.learn.mockitoPack.member.MemberService;
import mozell.learn.mockitoPack.study.StudyRepository;
import mozell.learn.mockitoPack.study.StudyService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class StudyServiceTest {

    @Test
    public void createStudyService() {
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }
}
