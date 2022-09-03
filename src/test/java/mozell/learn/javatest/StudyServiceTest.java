package mozell.learn.javatest;

import mozell.learn.mockitoPack.domain.Member;
import mozell.learn.mockitoPack.domain.Study;
import mozell.learn.mockitoPack.member.MemberNotFoundException;
import mozell.learn.mockitoPack.member.MemberService;
import mozell.learn.mockitoPack.study.StudyRepository;
import mozell.learn.mockitoPack.study.StudyService;
import org.junit.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudyServiceTest {

    @Test
    public void createStudyService() {
        MemberService memberService = new MemberService() {
            @Override
            public Optional<Member> findById(Long memberId) throws MemberNotFoundException {
                return Optional.empty();
            }
        };

        StudyRepository studyRepository = new StudyRepository() {
            @Override
            public List<mozell.learn.mockitoPack.domain.Study> findAll() {
                return null;
            }

            @Override
            public List<mozell.learn.mockitoPack.domain.Study> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<mozell.learn.mockitoPack.domain.Study> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public <S extends mozell.learn.mockitoPack.domain.Study> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends mozell.learn.mockitoPack.domain.Study> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends mozell.learn.mockitoPack.domain.Study> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<mozell.learn.mockitoPack.domain.Study> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public mozell.learn.mockitoPack.domain.Study getOne(Long aLong) {
                return null;
            }

            @Override
            public mozell.learn.mockitoPack.domain.Study getById(Long aLong) {
                return null;
            }

            @Override
            public <S extends mozell.learn.mockitoPack.domain.Study> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends mozell.learn.mockitoPack.domain.Study> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<mozell.learn.mockitoPack.domain.Study> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends mozell.learn.mockitoPack.domain.Study> S save(S entity) {
                return null;
            }

            @Override
            public Optional<mozell.learn.mockitoPack.domain.Study> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(mozell.learn.mockitoPack.domain.Study entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends mozell.learn.mockitoPack.domain.Study> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends mozell.learn.mockitoPack.domain.Study> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends mozell.learn.mockitoPack.domain.Study> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends mozell.learn.mockitoPack.domain.Study> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends mozell.learn.mockitoPack.domain.Study> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Study, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };

        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }
}
