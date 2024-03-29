package in.app.heal.repository;

import in.app.heal.entities.FlaggedBlogs;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface FlaggedBlogsRepository
    extends JpaRepository<FlaggedBlogs, Integer> {

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM flagged_blogs where id = ?1", nativeQuery = true)
  void deleteById(int id);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM flagged_blogs", nativeQuery = true)
  void deleteAll();

  List<FlaggedBlogs> findAll();

  Optional<FlaggedBlogs> findById(int id);

  @Query(value = "SELECT * FROM flagged_blogs where user_id = ?1",
         nativeQuery = true)
  List<FlaggedBlogs>
  findByUserId(int userId);

  @Query(value = "SELECT * FROM flagged_blogs where blog_id = ?1",
         nativeQuery = true)
  List<FlaggedBlogs>
  findByBlogId(int blogId);
}
