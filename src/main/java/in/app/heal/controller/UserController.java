package in .app.heal.controller;

import in.app.heal.aux.*;
import in.app.heal.entities.*;
import in.app.heal.service.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCredentialsService userCredentialsService;
    @Autowired
    private PublicQNAService publicQNAService;
    @Autowired
    private CommentService commentService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    Dotenv dotenv = Dotenv.load();

    @GetMapping(path = "/getProfile")
    public ResponseEntity < ? > getProfileDetails(@RequestHeader HttpHeaders headers) {
        String auth = headers.get("authorization").toString();
        return userCredentialsService.getProfileDetails(auth);
    }

    @PostMapping(path = "/register")
    public ResponseEntity < ? > registerUser(@RequestBody AuxUserDTO auxUserDTO) {
        return userCredentialsService.registerUser(auxUserDTO);
    }

    @PostMapping(path = "/login")
    public ResponseEntity < ? > loginUser(@RequestBody LoginDTO loginDTO) {
        return userCredentialsService.loginUser(loginDTO);
    }

    @PostMapping(path = "/addQuestion")
    public ResponseEntity < ? > addPublicQuestion(@RequestBody AuxPublicQNADTO auxPublicQNADTO) {
      return publicQNAService.addPublicQuestion(auxPublicQNADTO);
    }

    @PutMapping(path = "/editQuestion")
    public ResponseEntity < ? > editPublicQuestion(@RequestBody AuxPublicQNAEditDTO auxPublicQNAEditDTO) {
        return publicQNAService.editPublicQuestion(auxPublicQNAEditDTO);
    }

    @DeleteMapping(path = "/deleteQuestion/{questionId}")
    public ResponseEntity < ? > deleteQuestion(@PathVariable Integer questionId) {
        Optional < PublicQNA > questionFound = publicQNAService.findById(questionId);
        if (questionFound.isPresent()) {
            publicQNAService.deleteById(questionId);
            return new ResponseEntity < > (HttpStatus.OK);
        }
        return new ResponseEntity < > (HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/allQuestions")
    public ResponseEntity < ? > findAllPublicQuestions() {
        Optional < List < PublicQNA >> allQuestions = publicQNAService.findAll();
        return new ResponseEntity < Optional < List < PublicQNA >>> (allQuestions,
            HttpStatus.OK);
    }

    @PostMapping(path = "/comment")
    public ResponseEntity < ? > addComment(@RequestBody AuxCommentDTO auxCommentDTO) {
        Optional < PublicQNA > questionFound = publicQNAService.findById(auxCommentDTO.getQuestionId());
        Optional < User > userFound = userService.findById(auxCommentDTO.getUserId());
        if (questionFound.isPresent() && userFound.isPresent()) {
            Comments comment = new Comments();
            comment.setComment(auxCommentDTO.getComment());
            comment.setUser_id(userFound.get());
            comment.setPublic_qna_id(questionFound.get());
            comment.setComment_date(new Date());
            commentService.addComment(comment);
            return new ResponseEntity < > (HttpStatus.OK);
        }
        return new ResponseEntity < > (HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/editComment")
    public ResponseEntity < ? > editComment(@RequestBody AuxCommentEditDTO auxCommentEditDTO) {
        Optional < PublicQNA > questionFound = publicQNAService.findById(auxCommentEditDTO.getQuestionId());
        Optional < User > userFound = userService.findById(auxCommentEditDTO.getUserId());
        Optional < Comments > commentsFound = commentService.findById(auxCommentEditDTO.getCommentId());
        if (questionFound.isPresent() && userFound.isPresent() &&
            commentsFound.isPresent()) {
            Comments comment = commentsFound.get();
            comment.setComment(auxCommentEditDTO.getComment());
            comment.setUser_id(userFound.get());
            comment.setPublic_qna_id(questionFound.get());
            comment.setComment_date(new Date());
            commentService.addComment(comment);
            return new ResponseEntity < > (HttpStatus.OK);
        }
        return new ResponseEntity < > (HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/deleteComment/{commentId}")
    public ResponseEntity < ? > deleteComment(@PathVariable Integer commentId) {
        Optional < Comments > commentsFound = commentService.findById(commentId);
        if (commentsFound.isPresent()) {
            Comments comment = commentsFound.get();
            commentService.delete(comment);
            return new ResponseEntity < > (HttpStatus.OK);
        }
        return new ResponseEntity < > (HttpStatus.NOT_FOUND);
    }
    @PutMapping(path = "/approve/{commentId}")
    public ResponseEntity < ? > approveComment(@PathVariable Integer commentId) {
        commentService.approveComment(commentId);
        return new ResponseEntity < > (HttpStatus.OK);
    }
    @GetMapping(path = "/allComments/{questionId}")
    public ResponseEntity < ? > findAllComments(@PathVariable Integer questionId) {
        Optional < List < Comments >> allComments = commentService.findAllByQuestionId(questionId);
        return new ResponseEntity < Optional < List < Comments >>> (allComments, HttpStatus.OK);
    }
}