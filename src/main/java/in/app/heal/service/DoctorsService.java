package in.app.heal.service;

import in.app.heal.aux.AuxDoctorsDTO;
import in.app.heal.entities.Doctors;
import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import in.app.heal.error.ApiError;
import in.app.heal.repository.DoctorsRepository;
import in.app.heal.repository.UserCredentialsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DoctorsService {

  @Autowired private DoctorsRepository repository;
  @Autowired private UserCredentialsRepository userCredentialsRepository;
  @Autowired private UserService userService;
  @Autowired private UserCredentialsService userCredentialsService;
  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public List<Doctors> getDoctorsAll() { return repository.findAll(); }

  public Optional<Doctors> getDoctorById(int id) {
    return repository.findById(id);
  }

  public ResponseEntity<?> getDoctorByUserId(int userId) {
    List<Doctors> doctor = repository.findByUserId(userId);
    if (doctor.size() > 0) {
      return new ResponseEntity<>(doctor, HttpStatus.OK);
    } else {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("Doctor not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
  }

  public List<Doctors> getDoctorBySpeciality(String speciality) {
    return repository.findBySpeciality(speciality);
  }

  public ResponseEntity<?> addDoctor(AuxDoctorsDTO auxDoctorDTO) {
    Doctors doctor = new Doctors();
    Optional<UserCredentials> alreadyExisting =
        userCredentialsService.findByEmail(auxDoctorDTO.getEmail());
    if (alreadyExisting.isPresent()) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("User already exists");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
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
    doctor.setUser_id(newUserCredentials.getUser_id());
    doctor.setSpecialization(auxDoctorDTO.getSpecialization());
    doctor.setExperience(auxDoctorDTO.getExperience());
    doctor.setDegree(auxDoctorDTO.getDegree());
    doctor.setLicense_number(auxDoctorDTO.getLicense_number());
    try {
      return new ResponseEntity<>(repository.save(doctor), HttpStatus.OK);
    } catch (Exception e) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.BAD_REQUEST);
      apiError.setMessage(e.getMessage());
      return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<?> deleteDoctor(int id) {
    if (repository.findById(id).isPresent()) {
      repository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("Doctor not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
  }

  public void deleteAll() { repository.deleteAll(); }

  public ResponseEntity<?> updateDoctor(AuxDoctorsDTO auxDoctorDTO) {
    Doctors doctor = new Doctors();
    Optional<Doctors> doctorOptional =
        this.getDoctorById(auxDoctorDTO.getDoctor_id());
    if (doctorOptional.isPresent()) {
      doctor = doctorOptional.get();
      doctor.setDoctor_id(auxDoctorDTO.getDoctor_id());
      doctor.setDoctor_id(auxDoctorDTO.getDoctor_id());
      Optional<User> user = userService.fetchById(auxDoctorDTO.getUser_id());
      if (user.isPresent()) {
        doctor.setUser_id(user.get());

        doctor.setSpecialization(auxDoctorDTO.getSpecialization());
        doctor.setExperience(auxDoctorDTO.getExperience());
        doctor.setDegree(auxDoctorDTO.getDegree());
        doctor.setLicense_number(auxDoctorDTO.getLicense_number());
        try {
          return new ResponseEntity<>(repository.save(doctor), HttpStatus.OK);
        } catch (Exception e) {
          ApiError apiError = new ApiError();
          apiError.setStatus(HttpStatus.BAD_REQUEST);
          apiError.setMessage(e.getMessage());
          return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
      } else {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.CONFLICT);
        apiError.setMessage("User not found");
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
      }
    } else {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("Doctor not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
  }
}
