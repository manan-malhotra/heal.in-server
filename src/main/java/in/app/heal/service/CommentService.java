package in.app.heal.service;

import in.app.heal.entities.Comments;
import in.app.heal.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    public void addComment(Comments comments){
        repository.save(comments);
    }

    public Optional<Comments> findById(Integer commentId){
        return repository.findById(commentId);
    }
    public void delete(Comments comments){
        repository.delete(comments);
    }

    public Optional<List<Comments>> findAllByQuestionId(Integer questionId){
        return repository.findAllByPublicQnaId(questionId);
    }
}
