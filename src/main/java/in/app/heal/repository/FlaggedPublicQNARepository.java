package in.app.heal.repository;

import in.app.heal.entities.FlaggedPublicQNA;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlaggedPublicQNARepository
    extends JpaRepository<FlaggedPublicQNA, Integer> {

  @Query(value = "SELECT * FROM flagged_public_qna", nativeQuery = true)
  List<FlaggedPublicQNA> findAll();

  @Query(value = "SELECT * FROM flagged_public_qna where user_id = ?1",
         nativeQuery = true)
  List<FlaggedPublicQNA>
  findByUserId(int userId);

  @Query(value = "SELECT * FROM flagged_public_qna where public_qna_id = ?1",
         nativeQuery = true)
  List<FlaggedPublicQNA>
  findByPublicQNAId(int publicQNAId);

  @Query(value = "SELECT * FROM flagged_public_qna where id = ?1",
         nativeQuery = true)
  Optional<FlaggedPublicQNA>
  findById(int id);

  @Query(value = "SELECT * FROM flagged_public_qna where user_id = ?1",
         nativeQuery = true)
  List<FlaggedPublicQNA>
  getFlaggedByUserId(int userId);
}
