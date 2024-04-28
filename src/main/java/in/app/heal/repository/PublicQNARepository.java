package in.app.heal.repository;

import in.app.heal.entities.PublicQNA;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PublicQNARepository extends JpaRepository<PublicQNA, Integer> {
    @Query(value = "Select * from public_qna order by added_date desc",nativeQuery = true)
    public Optional<List<PublicQNA>> findAllOrdered();

    @Query(value= "Delete from public_qna where user_id = ?1",nativeQuery = true)
    @Modifying
    @Transactional
    public void deleteByUserId(int userId);
}
