package mozell.learn.mockitoPack.member;

import mozell.learn.mockitoPack.domain.Member;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

    void validate(Long memberId);

}
