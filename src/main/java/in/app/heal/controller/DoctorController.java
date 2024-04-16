package in.app.heal.controller;

import in.app.heal.aux.AuxDoctorsDTO;
import in.app.heal.aux.AuxUserDTO;
import in.app.heal.entities.Doctors;
import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import in.app.heal.service.DoctorsService;
import in.app.heal.service.GenerateTokenService;
import in.app.heal.service.UserCredentialsService;
import in.app.heal.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.Optional;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

  @Autowired private DoctorsService doctorsService;
  @Autowired private UserCredentialsService userCredentialsService;
  @Autowired private GenerateTokenService generateTokenService;
  @Autowired private UserService userService;
  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  Dotenv dotenv = Dotenv.load();

  @PostMapping("/addDoctor")
  public ResponseEntity<?> addDoctor(@RequestBody AuxDoctorsDTO auxDoctorDTO) {
    Doctors doctor = new Doctors();
    // Optional<User> user = userService.findById(auxDoctorDTO.getUser_id());
    // if (user.isPresent()) {
    // doctor.setUser_id(user.get());
    // }
    Optional<UserCredentials> alreadyExisting =
        userCredentialsService.findByEmail(auxDoctorDTO.getEmail());
    if (alreadyExisting.isPresent()) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    User newUser = new User();
    newUser.setFirst_name(auxDoctorDTO.getFirstName());
    newUser.setLast_name(auxDoctorDTO.getLastName());
    newUser.setContact_number(auxDoctorDTO.getContact());
    newUser.setAge(auxDoctorDTO.getAge());
    newUser.setGender(auxDoctorDTO.getGender());
    newUser = userService.addUser(newUser);
    UserCredentials newUserCredentials = new UserCredentials();
    newUserCredentials.setEmail(auxDoctorDTO.getEmail());
    newUserCredentials.setUser_id(newUser);
    String hash = passwordEncoder.encode(auxDoctorDTO.getPassword());
    newUserCredentials.setPassword(hash);
    newUserCredentials.setRole(auxDoctorDTO.getRole());
    userCredentialsService.addUser(newUserCredentials);
    String secret = dotenv.get("SECRET_KEY");
    String jwtToken =generateTokenService.generateToken(auxDoctorDTO.getEmail());

    doctor.setUser_id(newUserCredentials.getUser_id());

    doctor.setSpecialization(auxDoctorDTO.getSpecialization());
    doctor.setExperience(auxDoctorDTO.getExperience());
    doctor.setDegree(auxDoctorDTO.getDegree());
    doctor.setLicense_number(auxDoctorDTO.getLicense_number());
    doctorsService.addDoctor(doctor);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/getAll")
  public ResponseEntity<?> getDoctorsAll() {
    List<Doctors> doctorList = doctorsService.getDoctorsAll();
    return new ResponseEntity<>(doctorList, HttpStatus.OK);
  }

  @GetMapping("getDoctorById/{id}")
  public ResponseEntity<?> getDoctorById(@PathVariable("id") int id) {
    List<Doctors> doctor = doctorsService.getDoctorByUserId(id);
    return new ResponseEntity<>(doctor, HttpStatus.OK);
  }

  @DeleteMapping("deleteDoctorById/{id}")
  public ResponseEntity<?> deleteDoctor(@PathVariable("id") int id) {
    doctorsService.deleteDoctor(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("deleteAll")
  public ResponseEntity<?> deleteAll() {
    doctorsService.deleteAll();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("updateDoctor")
  public ResponseEntity<?>
  updateDoctor(@RequestBody AuxDoctorsDTO auxDoctorDTO) {
    Doctors doctor = new Doctors();
    Optional<Doctors> doctorOptional =
        doctorsService.getDoctorById(auxDoctorDTO.getDoctor_id());
    doctor.setDoctor_id(auxDoctorDTO.getDoctor_id());
    doctor.setDoctor_id(auxDoctorDTO.getDoctor_id());
    Optional<User> user = userService.fetchById(auxDoctorDTO.getUser_id());
    if (user.isPresent()) {
      doctor.setUser_id(user.get());
    }
    doctor.setSpecialization(auxDoctorDTO.getSpecialization());
    doctor.setExperience(auxDoctorDTO.getExperience());
    doctor.setDegree(auxDoctorDTO.getDegree());
    doctor.setLicense_number(auxDoctorDTO.getLicense_number());
    doctorsService.updateDoctor(doctor);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
