package in.app.heal.repository;

import in.app.heal.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comments,Integer> {

    @Query(value = "Select * from comments where public_qna_id=?1 order by comment_date",nativeQuery = true)
    public Optional<List<Comments>> findAllByPublicQnaId(Integer questionId);
}
