package in.app.heal.repository;

import in.app.heal.entities.DepressionTest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DepressionTestRepository
    extends JpaRepository<DepressionTest, Integer> {
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM depression_test where id = ?1",
         nativeQuery = true)
  void
  deleteById(int id);

  @Query(value = "DELETE FROM depression_test", nativeQuery = true)
  void deleteAll();

  List<DepressionTest> findAll();
}
