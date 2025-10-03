package ma.mohammedounzar.resumeparser.repository;

import ma.mohammedounzar.resumeparser.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeParserRepository extends JpaRepository<Resume, Long> {
}
