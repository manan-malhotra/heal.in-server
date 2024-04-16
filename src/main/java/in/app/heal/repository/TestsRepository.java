package in.app.heal.repository;

import in.app.heal.entities.Comments;
import in.app.heal.entities.Tests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestsRepository extends JpaRepository<Tests,Integer> {
    @Query(value = "SELECT tests.title FROM test_scores JOIN tests ON test_scores.test_id = tests.test_id WHERE test_scores.test_id = ?1", nativeQuery = true)
    List<String> getTestName(int testId);
}
