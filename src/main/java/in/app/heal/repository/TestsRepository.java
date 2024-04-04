package in.app.heal.repository;

import in.app.heal.entities.Comments;
import in.app.heal.entities.Tests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestsRepository extends JpaRepository<Tests,Integer> {
}
