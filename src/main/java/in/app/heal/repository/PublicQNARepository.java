package in.app.heal.repository;

import in.app.heal.entities.PublicQNA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PublicQNARepository extends JpaRepository<PublicQNA, Integer> {
    @Query(value = "Select * from public_qna order by added_date desc",nativeQuery = true)
    public Optional<List<PublicQNA>> findAllOrdered();
}
