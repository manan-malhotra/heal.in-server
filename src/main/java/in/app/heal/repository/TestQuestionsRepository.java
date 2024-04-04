package in.app.heal.repository;

import in.app.heal.entities.TestQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestQuestionsRepository extends JpaRepository<TestQuestions,Integer> {
    @Query(value = "SELECT * FROM test_questions where test_id = ?1", nativeQuery = true)
    List<TestQuestions> findByTestId(int test_id);
}
