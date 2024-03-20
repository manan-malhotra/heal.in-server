package in.app.heal.controller;

import in.app.heal.aux.*;
import in.app.heal.entities.*;
import in.app.heal.service.*;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    private JournalEntryService journalEntryService;
    @Autowired
    private CommentService commentService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    Dotenv dotenv = Dotenv.load();
    @GetMapping(path = "/getProfile")
    public ResponseEntity<?> getProfileDetails(@RequestHeader HttpHeaders headers){
        String auth = headers.get("authorization").toString();
        String token = "";
        if(!auth.isEmpty()){
            token = auth.split(" ")[1];
        }
        String secret = dotenv.get("SECRET_KEY");
        Key hmackey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
        try{
            Claims jwt = Jwts.parserBuilder().setSigningKey(hmackey).build().parseClaimsJws(token).getBody();
            String email = (String) jwt.get("email");
            Optional<UserCredentials> userCredentialsOptional = userCredentialsService.findByEmail(email);
            if(userCredentialsOptional.isPresent()){
                UserCredentials userCredentials = userCredentialsOptional.get();
                User user = userCredentials.getUser_id();
                AuxUserDTO auxUserDTO = new AuxUserDTO();
                auxUserDTO.setAge(user.getAge());
                auxUserDTO.setGender(user.getGender());
                auxUserDTO.setEmail(email);
                auxUserDTO.setRole(userCredentials.getRole());
                auxUserDTO.setContact(user.getContact_number());
                auxUserDTO.setFirstName(user.getFirst_name());
                auxUserDTO.setLastName(user.getLast_name());
                auxUserDTO.setUserId(user.getUser_id());
                return new ResponseEntity<AuxUserDTO>(auxUserDTO,HttpStatus.OK);
            }
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        }

    }
    @PostMapping(path="/register")
    public ResponseEntity<?> registerUser(@RequestBody AuxUserDTO auxUserDTO){
        User newUser = new User();
        newUser.setFirst_name(auxUserDTO.getFirstName());
        newUser.setLast_name(auxUserDTO.getLastName());
        newUser.setContact_number(auxUserDTO.getContact());
        newUser.setAge(auxUserDTO.getAge());
        newUser.setGender(auxUserDTO.getGender());
        newUser = userService.addUser(newUser);
        UserCredentials newUserCredentials = new UserCredentials();
        newUserCredentials.setEmail(auxUserDTO.getEmail());
        newUserCredentials.setUser_id(newUser);
        String hash = passwordEncoder.encode(auxUserDTO.getPassword());
        newUserCredentials.setPassword(hash);
        newUserCredentials.setRole(auxUserDTO.getRole());
        userCredentialsService.addUser(newUserCredentials);
        String secret = dotenv.get("SECRET_KEY");
        String jwtToken = Jwts.builder().signWith(SignatureAlgorithm.HS256,secret)
                .claim("email",auxUserDTO.getEmail())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(30l, ChronoUnit.DAYS))).compact();
        return new ResponseEntity<String>(jwtToken,HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> registerUser(@RequestBody LoginDTO loginDTO){
        String secret = dotenv.get("SECRET_KEY");
        Optional<UserCredentials> userCredentials = userCredentialsService.findByEmail(loginDTO.getEmail());
        if(userCredentials.isPresent()){
            UserCredentials userCredentialsfound = userCredentials.get();
            String password = userCredentialsfound.getPassword();
            boolean match = passwordEncoder.matches( loginDTO.getPassword(),password);
            if(match){
                String jwtToken = Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256,secret)
                        .claim("email",loginDTO.getEmail())
                        .setIssuedAt(Date.from(Instant.now()))
                        .setExpiration(Date.from(Instant.now().plus(30l,ChronoUnit.DAYS))).compact();
                return new ResponseEntity<String>(jwtToken,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/addQuestion")
    public ResponseEntity<?>  addPublicQuestion(@RequestBody AuxPublicQNADTO auxPublicQNADTO){
        Optional<User> userFound = userService.findById(auxPublicQNADTO.getUserId());
        if(userFound.isPresent()) {
            User user = userFound.get();
            PublicQNA newQuestion = new PublicQNA();
            newQuestion.setUser_id(user);
            newQuestion.setQuestion(auxPublicQNADTO.getQuestion());
            newQuestion.setDescription(auxPublicQNADTO.getDescription());
            newQuestion.setAdded_date(new Date());
            publicQNAService.addQuestion(newQuestion);
            return new ResponseEntity<PublicQNA>(newQuestion,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/editQuestion")
    public ResponseEntity<?>  editPublicQuestion(@RequestBody AuxPublicQNAEditDTO auxPublicQNAEditDTO){
        Optional<PublicQNA> questionFound = publicQNAService.findById(auxPublicQNAEditDTO.getQuestionId());
        if(questionFound.isPresent()){
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
    public ResponseEntity<?> deleteQuestion(@PathVariable Integer questionId){
        Optional<PublicQNA> questionFound = publicQNAService.findById(questionId);
        if(questionFound.isPresent()) {
            publicQNAService.deleteById(questionId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/allQuestions")
    public ResponseEntity<?> findAllPublicQuestions(){
        Optional<List<PublicQNA>> allQuestions = publicQNAService.findAll();
        return new ResponseEntity<Optional<List<PublicQNA>>>(allQuestions,HttpStatus.OK);
    }

    @PostMapping(path = "/comment")
    public ResponseEntity<?> addComment(@RequestBody AuxCommentDTO auxCommentDTO){
        Optional<PublicQNA> questionFound = publicQNAService.findById(auxCommentDTO.getQuestionId());
        Optional<User> userFound = userService.findById(auxCommentDTO.getUserId());
        if(questionFound.isPresent() && userFound.isPresent()) {
            Comments comment = new Comments();
            comment.setComment(auxCommentDTO.getComment());
            comment.setUser_id(userFound.get());
            comment.setPublic_qna_id(questionFound.get());
            comment.setComment_date(new Date());
            commentService.addComment(comment);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping(path = "/editComment")
    public ResponseEntity<?> editComment(@RequestBody AuxCommentEditDTO auxCommentEditDTO){
        Optional<PublicQNA> questionFound = publicQNAService.findById(auxCommentEditDTO.getQuestionId());
        Optional<User> userFound = userService.findById(auxCommentEditDTO.getUserId());
        Optional<Comments> commentsFound = commentService.findById(auxCommentEditDTO.getCommentId());
        if(questionFound.isPresent() && userFound.isPresent() && commentsFound.isPresent()) {
            Comments comment = commentsFound.get();
            comment.setComment(auxCommentEditDTO.getComment());
            comment.setUser_id(userFound.get());
            comment.setPublic_qna_id(questionFound.get());
            comment.setComment_date(new Date());
            commentService.addComment(comment);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/deleteComment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId){
        Optional<Comments> commentsFound = commentService.findById(commentId);
        if(commentsFound.isPresent()){
            Comments comment = commentsFound.get();
            commentService.delete(comment);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/allComments/{questionId}")
    public ResponseEntity<?> findAllComments(@PathVariable Integer questionId){
        Optional<List<Comments>> allComments = commentService.findAllByQuestionId(questionId);
        return new ResponseEntity<Optional<List<Comments>>>(allComments,HttpStatus.OK);
    }


}
