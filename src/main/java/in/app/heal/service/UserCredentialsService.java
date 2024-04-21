package in.app.heal.service;

import in.app.heal.aux.AuxChangePasswordDTO;
import in.app.heal.aux.AuxForgotPasswordDTO;
import in.app.heal.aux.AuxUserDTO;
import in.app.heal.aux.LoginDTO;
import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import in.app.heal.error.ApiError;
import in.app.heal.repository.UserCredentialsRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class UserCredentialsService {
  @Autowired private TokenService tokenService;
  @Autowired private UserService userService;
  @Autowired private UserCredentialsRepository repository;
  @Autowired private JavaMailSender mailSender;
  @Autowired private TemplateEngine templateEngine;
  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  public void addUser(UserCredentials userCredentials) {
    repository.save(userCredentials);
  }
  public Optional<UserCredentials> findByEmail(String email) {
    return Optional.ofNullable(repository.findByEmail(email));
  }

  public ResponseEntity<?> loginUser(LoginDTO loginDTO) {
    Optional<UserCredentials> userCredentials =
        this.findByEmail(loginDTO.getEmail());
    if (userCredentials.isPresent()) {
      UserCredentials userCredentialsfound = userCredentials.get();
      String password = userCredentialsfound.getPassword();
      boolean match = passwordEncoder.matches(loginDTO.getPassword(), password);
      if (match) {
        String jwtToken = tokenService.generateToken(loginDTO.getEmail());
        return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  public ResponseEntity<?> getProfileDetails(String auth) {
    String token = tokenService.getToken(auth);
    try {
      String email = tokenService.getEmailFromToken(token);
      Optional<UserCredentials> userCredentialsOptional =
          this.findByEmail(email);
      if (userCredentialsOptional.isPresent()) {
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
        return new ResponseEntity<AuxUserDTO>(auxUserDTO, HttpStatus.OK);
      }
      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    }
  }

  public ResponseEntity<?> registerUser(AuxUserDTO auxUserDTO) {
    Optional<UserCredentials> alreadyExisting =
        this.findByEmail(auxUserDTO.getEmail());
    if (alreadyExisting.isPresent()) {
      System.out.println("User already exists");
      ApiError apiError = new ApiError();
      apiError.setMessage("User already exists");
      apiError.setStatus(HttpStatus.CONFLICT);
      return new ResponseEntity<Object>(apiError, HttpStatus.CONFLICT);
    }
    User user = userService.populateUser(auxUserDTO);
    UserCredentials newUserCredentials = new UserCredentials();
    newUserCredentials.setEmail(auxUserDTO.getEmail());
    newUserCredentials.setUser_id(user);
    String hash = passwordEncoder.encode(auxUserDTO.getPassword());
    newUserCredentials.setPassword(hash);
    newUserCredentials.setRole(auxUserDTO.getRole());
    this.addUser(newUserCredentials);
    String jwtToken = tokenService.generateToken(auxUserDTO.getEmail());
    return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
  }

  public void sendEmail(String email, String subject, String message) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
    try {
      helper.setTo(email);
      helper.setSubject(subject);
      Context context = new Context();
      context.setVariable("message", message);
      String htmlContent =
          templateEngine.process("email_otp_template.html", context);
      helper.setText(htmlContent, true);
      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  public ResponseEntity<?>
  forgotPassword(AuxForgotPasswordDTO forgotPasswordDTO) {
    Optional<UserCredentials> alreadyExisting =
        this.findByEmail(forgotPasswordDTO.getEmail());
    if (alreadyExisting.isEmpty()) {
      System.out.println("User does not exist");
      ApiError apiError = new ApiError();
      apiError.setMessage("User does not exist");
      apiError.setStatus(HttpStatus.CONFLICT);
      return new ResponseEntity<Object>(apiError, HttpStatus.CONFLICT);
    }
    Random random = new Random();
    int otp = 1000 + random.nextInt(9000);
    String email = forgotPasswordDTO.getEmail();
    String subject = "Forgot Password";
    String message = "" + otp;
    sendEmail(email, subject, message);
    return new ResponseEntity<Integer>(otp, HttpStatus.OK);
  }

  public ResponseEntity<?>
  changePassword(AuxChangePasswordDTO changePasswordDTO) {
    Optional<UserCredentials> alreadyExisting =
        this.findByEmail(changePasswordDTO.getEmail());
    if (alreadyExisting.isEmpty()) {
      System.out.println("User does not exist");
      ApiError apiError = new ApiError();
      apiError.setMessage("User does not exist");
      apiError.setStatus(HttpStatus.CONFLICT);
      return new ResponseEntity<Object>(apiError, HttpStatus.CONFLICT);
    }
    String hash = passwordEncoder.encode(changePasswordDTO.getPassword());
    alreadyExisting.get().setPassword(hash);
    this.addUser(alreadyExisting.get());
    return new ResponseEntity<String>(HttpStatus.OK);
  }

  public ResponseEntity<?>
  updatePassword(AuxChangePasswordDTO changePasswordDTO) {
    Optional<UserCredentials> alreadyExisting =
        this.findByEmail(changePasswordDTO.getEmail());
    if (alreadyExisting.isEmpty()) {
      System.out.println("User does not exist");
      ApiError apiError = new ApiError();
      apiError.setMessage("User does not exist");
      apiError.setStatus(HttpStatus.CONFLICT);
      return new ResponseEntity<Object>(apiError, HttpStatus.CONFLICT);
    }
    if (alreadyExisting.isPresent()) {
      UserCredentials userCredentialsfound = alreadyExisting.get();
      String password = userCredentialsfound.getPassword();
      boolean match = passwordEncoder.matches(
          changePasswordDTO.getCurrentPassword(), password);
      if (match) {
        String hash = passwordEncoder.encode(changePasswordDTO.getPassword());
        alreadyExisting.get().setPassword(hash);
        this.addUser(alreadyExisting.get());
        return new ResponseEntity<String>(HttpStatus.OK);
      } else {
        System.out.println("Password does not match");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
    }
    return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
  }
}
