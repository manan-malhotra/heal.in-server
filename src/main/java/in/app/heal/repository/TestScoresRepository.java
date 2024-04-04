package in.app.heal.repository;

import in.app.heal.entities.TestScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestScoresRepository extends JpaRepository<TestScores,Integer> {
    @Query(value = "SELECT * FROM test_scores where user_id = ?1 order by submitted_date desc", nativeQuery = true)
    List<TestScores> getScores(int user_id);
}
