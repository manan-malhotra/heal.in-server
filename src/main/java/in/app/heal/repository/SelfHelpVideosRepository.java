package in.app.heal.repository;

import in.app.heal.entities.SelfHelpVideos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SelfHelpVideosRepository
    extends JpaRepository<SelfHelpVideos, Integer> {

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM self_help_videos where id = ?1",
         nativeQuery = true)
  Integer
  deleteById(int id);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM self_help_videos", nativeQuery = true)
  void deleteAll();

  @Modifying
  @Transactional
  @Query(
      value = "UPDATE self_help_videos SET title = ?1, url = ?2 where id = ?3",
      nativeQuery = true)
  Integer
  updateById(String title, String url, int id);

  List<SelfHelpVideos> findAll();
}
