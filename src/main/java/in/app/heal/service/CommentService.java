package in.app.heal.service;

import in.app.heal.aux.AuxCommentDTO;
import in.app.heal.aux.AuxCommentEditDTO;
import in.app.heal.entities.Comments;
import in.app.heal.entities.PublicQNA;
import in.app.heal.entities.User;
import in.app.heal.error.ApiError;
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
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.CONFLICT);
        if (!questionFound.isPresent()) {
            apiError.setMessage("Question not found");
        }else{
            apiError.setMessage("User not found");
        }
        return new ResponseEntity < > (apiError, HttpStatus.CONFLICT);
    }

    public Optional<Comments> findById(Integer commentId){
        return repository.findById(commentId);
    }

    public void delete(Comments comments){
        repository.delete(comments);
    }

    public ResponseEntity < ? > findAllComments(Integer questionId){
        Optional < PublicQNA > questionFound = publicQNAService.findById(questionId);
        if(!questionFound.isPresent()){
            ApiError apiError = new ApiError();
            apiError.setStatus(HttpStatus.CONFLICT);
            apiError.setMessage("Question not found");
            return new ResponseEntity < > (apiError,HttpStatus.CONFLICT);
        }
        Optional<List<Comments>> comments = repository.findAllByPublicQnaId(questionId);
        return new ResponseEntity<Optional<List<Comments>>>(comments,HttpStatus.OK);
    }
    
    public ResponseEntity<?> approveComment(Integer commentId){
        Optional<Comments> commentsFound = repository.findById(commentId);
        if(commentsFound.isPresent()){
            Comments comment = commentsFound.get();
            comment.setApproved(true);
            return new ResponseEntity<>(repository.save(comment),HttpStatus.OK);
        }
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.CONFLICT);
        apiError.setMessage("Comment not found");
        return new ResponseEntity<>(apiError,HttpStatus.CONFLICT);
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
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.CONFLICT);
        if (!questionFound.isPresent()) {
            apiError.setMessage("Question not found");
        }else if(!userFound.isPresent()){
            apiError.setMessage("User not found");
        }else{
            apiError.setMessage("Comment not found");
        }
        return new ResponseEntity < > (apiError, HttpStatus.CONFLICT);
    }

    public ResponseEntity < ? > deleteComment(Integer commentId) {
        Optional < Comments > commentsFound = this.findById(commentId);
        if (commentsFound.isPresent()) {
            Comments comment = commentsFound.get();
            this.delete(comment);
            return new ResponseEntity < > (HttpStatus.OK);
        }
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.CONFLICT);
        apiError.setMessage("Comment not found");
        return new ResponseEntity < > (apiError,HttpStatus.CONFLICT);
    }
}
