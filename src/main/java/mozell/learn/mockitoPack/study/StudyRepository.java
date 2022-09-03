package mozell.learn.mockitoPack.study;

import mozell.learn.mockitoPack.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
