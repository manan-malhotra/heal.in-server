package in.app.heal.repository;

import in.app.heal.entities.Tests;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestsRepository extends JpaRepository<Tests,Integer> {
    @Query("SELECT t FROM Tests t WHERE t.test_name = :test_name")
    Optional<Tests> findByTestName(String test_name);
}
