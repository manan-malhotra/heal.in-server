package in.app.heal.controller;

import in.app.heal.aux.*;
import in.app.heal.entities.JournalEntry;
import in.app.heal.entities.PublicQNA;
import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import in.app.heal.service.JournalEntryService;
import in.app.heal.service.PublicQNAService;
import in.app.heal.service.UserCredentialsService;
import in.app.heal.service.UserService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(path = "api/user")
public class UserController {

  @Autowired private UserService userService;
  @Autowired private UserCredentialsService userCredentialsService;
  @Autowired private JournalEntryService journalEntryService;
  @PostMapping(path = "/register")
  public ResponseEntity<?> registerUser(@RequestBody AuxUserDTO auxUserDTO) {
    User newUser = new User();
    System.out.println("TESTING");
    newUser.setFirst_name(auxUserDTO.getFirstName());
    newUser.setLast_name(auxUserDTO.getLastName());
    newUser.setContact_number(auxUserDTO.getContact());
    newUser.setAge(auxUserDTO.getAge());
    newUser.setGender(auxUserDTO.getGender());
    newUser = userService.addUser(newUser);
    UserCredentials newUserCredentials = new UserCredentials();
    newUserCredentials.setEmail(auxUserDTO.getEmail());
    newUserCredentials.setUser_id(newUser);
    newUserCredentials.setPassword(auxUserDTO.getPassword()); // to be hashed
    newUserCredentials.setRole(auxUserDTO.getRole());
    userCredentialsService.addUser(newUserCredentials);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(path = "/login")
  public ResponseEntity<?> registerUser(@RequestBody LoginDTO loginDTO) {
    Optional<UserCredentials> userCredentials =
        userCredentialsService.findByEmail(loginDTO.getEmail());
    if (userCredentials.isPresent()) {
      UserCredentials userCredentialsfound = userCredentials.get();
      String password = userCredentialsfound.getPassword();
      if (password.equals(loginDTO.getPassword())) {
        @Autowired private PublicQNAService publicQNAService;

        @Autowired private JournalEntryService journalEntryService;
        @PostMapping(path = "/register")
        public ResponseEntity<?> registerUser(
            @RequestBody AuxUserDTO auxUserDTO) {
          User newUser = new User();
          System.out.println("TESTING");
          newUser.setFirst_name(auxUserDTO.getFirstName());
          newUser.setLast_name(auxUserDTO.getLastName());
          newUser.setContact_number(auxUserDTO.getContact());
          newUser.setAge(auxUserDTO.getAge());
          newUser.setGender(auxUserDTO.getGender());
          newUser = userService.addUser(newUser);
          UserCredentials newUserCredentials = new UserCredentials();
          newUserCredentials.setEmail(auxUserDTO.getEmail());
          newUserCredentials.setUser_id(newUser);
          newUserCredentials.setPassword(
              auxUserDTO.getPassword()); // to be hashed
          newUserCredentials.setRole(auxUserDTO.getRole());
          userCredentialsService.addUser(newUserCredentials);
          return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> registerUser(@RequestBody LoginDTO loginDTO) {
      Optional<UserCredentials> userCredentials =
          userCredentialsService.findByEmail(loginDTO.getEmail());
      if (userCredentials.isPresent()) {
        UserCredentials userCredentialsfound = userCredentials.get();
        String password = userCredentialsfound.getPassword();
        if (password.equals(loginDTO.getPassword())) {
          return new ResponseEntity<>(HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/addQuestion")
    public ResponseEntity<?> addPublicQuestion(
        @RequestBody AuxPublicQNADTO auxPublicQNADTO) {
      Optional<User> userFound =
          userService.findById(auxPublicQNADTO.getUserId());
      if (userFound.isPresent()) {
        User user = userFound.get();
        PublicQNA newQuestion = new PublicQNA();
        newQuestion.setUser_id(user);
        newQuestion.setQuestion(auxPublicQNADTO.getQuestion());
        newQuestion.setDescription(auxPublicQNADTO.getDescription());
        newQuestion.setAdded_date(new Date());
        publicQNAService.addQuestion(newQuestion);
        return new ResponseEntity<PublicQNA>(newQuestion, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/editQuestion")
    public ResponseEntity<?> editPublicQuestion(
        @RequestBody AuxPublicQNAEditDTO auxPublicQNAEditDTO) {
      System.out.println(auxPublicQNAEditDTO.getQuestionId());
      Optional<PublicQNA> questionFound =
          publicQNAService.findById(auxPublicQNAEditDTO.getQuestionId());
      if (questionFound.isPresent()) {
        PublicQNA question = questionFound.get();
        question.setAdded_date(new Date());
        question.setDescription(auxPublicQNAEditDTO.getDescription());
        question.setQuestion(auxPublicQNAEditDTO.getQuestion());
        publicQNAService.addQuestion(question);
        return new ResponseEntity<>(HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/deleteQuestion/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Integer questionId) {
      Optional<PublicQNA> questionFound = publicQNAService.findById(questionId);
      if (questionFound.isPresent()) {
        publicQNAService.deleteById(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/allQuestions")
    public ResponseEntity<?> findAllPublicQuestions() {
      Optional<List<PublicQNA>> allQuestions = publicQNAService.findAll();
      return new ResponseEntity<Optional<List<PublicQNA>>>(allQuestions,
                                                           HttpStatus.OK);
    }
  }
