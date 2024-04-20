package in.app.heal.service;

import in.app.heal.aux.AuxCommentDTO;
import in.app.heal.aux.AuxCommentEditDTO;
import in.app.heal.entities.Comments;
import in.app.heal.entities.PublicQNA;
import in.app.heal.entities.User;
import in.app.heal.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private PublicQNAService publicQNAService;
    public void addComment(Comments comment){
        repository.save(comment);
    }

    public ResponseEntity < ? > addComment(AuxCommentDTO auxCommentDTO) {
        Optional < PublicQNA > questionFound = publicQNAService.findById(auxCommentDTO.getQuestionId());
        Optional < User > userFound = userService.findById(auxCommentDTO.getUserId());
        if (questionFound.isPresent() && userFound.isPresent()) {
            Comments comment = new Comments();
            comment.setComment(auxCommentDTO.getComment());
            comment.setUser_id(userFound.get());
            comment.setPublic_qna_id(questionFound.get());
            comment.setComment_date(new Date());
            this.addComment(comment);
            return new ResponseEntity < > (HttpStatus.OK);
        }
        return new ResponseEntity < > (HttpStatus.NOT_FOUND);
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
    
    public void approveComment(Integer commentId){
        Optional<Comments> commentsFound = repository.findById(commentId);
        if(commentsFound.isPresent()){
            Comments comment = commentsFound.get();
            comment.setApproved(true);
            repository.save(comment);
        }
    }

    public ResponseEntity < ? > editComment(AuxCommentEditDTO auxCommentEditDTO) {
    Optional < PublicQNA > questionFound = publicQNAService.findById(auxCommentEditDTO.getQuestionId());
        Optional < User > userFound = userService.findById(auxCommentEditDTO.getUserId());
        Optional < Comments > commentsFound = this.findById(auxCommentEditDTO.getCommentId());
        if (questionFound.isPresent() && userFound.isPresent() &&
            commentsFound.isPresent()) {
            Comments comment = commentsFound.get();
            comment.setComment(auxCommentEditDTO.getComment());
            comment.setUser_id(userFound.get());
            comment.setPublic_qna_id(questionFound.get());
            comment.setComment_date(new Date());
            this.addComment(comment);
            return new ResponseEntity < > (HttpStatus.OK);
        }
        return new ResponseEntity < > (HttpStatus.NOT_FOUND);
    }

    public ResponseEntity < ? > deleteComment(Integer commentId) {
        Optional < Comments > commentsFound = this.findById(commentId);
        if (commentsFound.isPresent()) {
            Comments comment = commentsFound.get();
            this.delete(comment);
            return new ResponseEntity < > (HttpStatus.OK);
        }
        return new ResponseEntity < > (HttpStatus.NOT_FOUND);
    }
}
