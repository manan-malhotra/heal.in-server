package in.app.heal.controller;

import in.app.heal.aux.*;
import in.app.heal.entities.*;
import in.app.heal.service.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "api/user")
public class UserController {

  @Autowired private UserCredentialsService userCredentialsService;
  @Autowired private PublicQNAService publicQNAService;
  @Autowired private CommentService commentService;

  Dotenv dotenv = Dotenv.load();

  @GetMapping(path = "/getProfile")
  public ResponseEntity<?> getProfileDetails(@RequestHeader HttpHeaders headers) {
    String auth = "";
    if(headers.get("authorization") != null) {
      auth = headers.get("authorization").toString();
    }
    return userCredentialsService.getProfileDetails(auth);
  }

  @PostMapping(path = "/register")
  public ResponseEntity<?> registerUser(@RequestBody AuxUserDTO auxUserDTO) {
    return userCredentialsService.registerUser(auxUserDTO);
  }

  @PostMapping(path = "/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
    return userCredentialsService.loginUser(loginDTO);
  }

  @PostMapping(path = "/addQuestion")
  public ResponseEntity<?>
  addPublicQuestion(@RequestBody AuxPublicQNADTO auxPublicQNADTO) {
    return publicQNAService.addPublicQuestion(auxPublicQNADTO);
  }

  @PutMapping(path = "/editQuestion")
  public ResponseEntity<?>
  editPublicQuestion(@RequestBody AuxPublicQNAEditDTO auxPublicQNAEditDTO) {
    return publicQNAService.editPublicQuestion(auxPublicQNAEditDTO);
  }

  @DeleteMapping(path = "/deleteQuestion/{questionId}")
  public ResponseEntity<?>
  deletePublicQuestion(@PathVariable Integer questionId) {
    return publicQNAService.deletePublicQuestion(questionId);
  }

  @GetMapping(path = "/allQuestions")
  public ResponseEntity<?> findAllPublicQuestions() {
    return new ResponseEntity<Optional<List<PublicQNA>>>(
        publicQNAService.findAll(), HttpStatus.OK);
  }

  @PostMapping(path = "/comment")
  public ResponseEntity<?> addComment(@RequestBody AuxCommentDTO auxCommentDTO) {
    return commentService.addComment(auxCommentDTO);
  }

  @PutMapping(path = "/editComment")
  public ResponseEntity<?>
  editComment(@RequestBody AuxCommentEditDTO auxCommentEditDTO) {
    return commentService.editComment(auxCommentEditDTO);
  }

  @DeleteMapping(path = "/deleteComment/{commentId}")
  public ResponseEntity<?> deleteComment(@PathVariable Integer commentId) {
    return commentService.deleteComment(commentId);
  }
  @PutMapping(path = "/approve/{commentId}")
  public ResponseEntity<?> approveComment(@PathVariable Integer commentId) {
    commentService.approveComment(commentId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  @GetMapping(path = "/allComments/{questionId}")
  public ResponseEntity<?> findAllComments(@PathVariable Integer questionId) {
    Optional<List<Comments>> allComments =
        commentService.findAllByQuestionId(questionId);
    return new ResponseEntity<Optional<List<Comments>>>(allComments,
                                                        HttpStatus.OK);
  }
  @PostMapping(path = "/forgotPassword")
  public ResponseEntity<?>
  forgotPassword(@RequestBody AuxForgotPasswordDTO forgotPasswordDTO) {
    return userCredentialsService.forgotPassword(forgotPasswordDTO);
  }
  @PostMapping(path = "/changePassword")
  public ResponseEntity<?>
  changePassword(@RequestBody AuxChangePasswordDTO changePasswordDTO) {
    return userCredentialsService.changePassword(changePasswordDTO);
  }
  @PostMapping(path = "/updatePassword")
  public ResponseEntity<?>
  updatePassword(@RequestBody AuxChangePasswordDTO changePasswordDTO) {
    return userCredentialsService.updatePassword(changePasswordDTO);
  }
}
