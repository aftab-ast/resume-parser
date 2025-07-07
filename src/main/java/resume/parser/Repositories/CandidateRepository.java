package resume.parser.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import resume.parser.Entities.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
