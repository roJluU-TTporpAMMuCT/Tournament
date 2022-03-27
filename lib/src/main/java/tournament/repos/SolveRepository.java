package tournament.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tournament.models.Solve;

@Repository
public interface SolveRepository extends JpaRepository<Solve, Long> {}
