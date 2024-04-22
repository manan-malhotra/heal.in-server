package in.app.heal.service;

import in.app.heal.aux.AuxPublicQNADTO;
import in.app.heal.aux.AuxPublicQNAEditDTO;
import in.app.heal.entities.PublicQNA;
import in.app.heal.entities.User;
import in.app.heal.error.ApiError;
import in.app.heal.repository.PublicQNARepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PublicQNAService {
  @Autowired private PublicQNARepository repository;
  @Autowired private UserService userService;

  public PublicQNA addQuestion(PublicQNA question) {
    repository.save(question);
    return question;
  }
  public Optional<PublicQNA> findById(Integer questionId) {
    return repository.findById(questionId);
  }
  public void deleteById(Integer questionId) {
    repository.deleteById(questionId);
  }
  public Optional<List<PublicQNA>> findAll() {
    return repository.findAllOrdered();
  }
  public ResponseEntity<?> addPublicQuestion(AuxPublicQNADTO auxPublicQNADTO) {
    Optional<User> userFound =
        userService.findById(auxPublicQNADTO.getUserId());
    if (userFound.isPresent()) {
      User user = userFound.get();
      PublicQNA newQuestion = new PublicQNA();
      newQuestion.setUser_id(user);
      newQuestion.setQuestion(auxPublicQNADTO.getQuestion());
      newQuestion.setAdded_date(new Date());
      this.addQuestion(newQuestion);
      return new ResponseEntity<PublicQNA>(newQuestion, HttpStatus.OK);
    }
    ApiError apiError = new ApiError();
    apiError.setStatus(HttpStatus.CONFLICT);
    apiError.setMessage("User not found");
    return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
  }

  public ResponseEntity<?>
  editPublicQuestion(AuxPublicQNAEditDTO auxPublicQNAEditDTO) {
    Optional<PublicQNA> questionFound =
        this.findById(auxPublicQNAEditDTO.getQuestionId());
    if (questionFound.isPresent()) {
      PublicQNA question = questionFound.get();
      question.setAdded_date(new Date());
      question.setQuestion(auxPublicQNAEditDTO.getQuestion());
      this.addQuestion(question);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    ApiError apiError = new ApiError();
    apiError.setStatus(HttpStatus.CONFLICT);
    apiError.setMessage("Question not found");
    return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
  }

  public ResponseEntity<?> deletePublicQuestion(Integer questionId) {
    Optional<PublicQNA> questionFound = this.findById(questionId);
    if (questionFound.isPresent()) {
      this.deleteById(questionId);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    ApiError apiError = new ApiError();
    apiError.setStatus(HttpStatus.CONFLICT);
    apiError.setMessage("Question not found");
    return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
  }
}
