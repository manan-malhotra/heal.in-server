package in.app.heal.repository;

import in.app.heal.entities.ADHDTest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ADHDTestRepository extends JpaRepository<ADHDTest, Integer> {

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM adhd_test where id = ?1", nativeQuery = true)
  void deleteById(int id);

  @Query(value = "SELECT * FROM adhd_test", nativeQuery = true)
  List<ADHDTest> findAll();

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM adhd_test", nativeQuery = true)
  void deleteAll();
}
