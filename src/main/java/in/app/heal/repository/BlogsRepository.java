package in.app.heal.repository;

import in.app.heal.entities.Blogs;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BlogsRepository extends JpaRepository<Blogs, Integer> {

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM blogs where blog_id = ?1", nativeQuery = true)
  void deleteById(int id);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM blogs", nativeQuery = true)
  void deleteAll();

  List<Blogs> findAll();
}
