package in.app.heal.controller;

import in.app.heal.aux.AuxDoctorsDTO;
import in.app.heal.entities.Doctors;
import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import in.app.heal.service.DoctorsService;
import in.app.heal.service.UserCredentialsService;
import in.app.heal.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

  @Autowired private DoctorsService doctorsService;
  @Autowired private UserCredentialsService userCredentialsService;
  @Autowired private UserService userService;
  Dotenv dotenv = Dotenv.load();

  @PostMapping("/addDoctor")
  public ResponseEntity<?> addDoctor(@RequestBody AuxDoctorsDTO auxDoctorDTO) {
    return doctorsService.addDoctor(auxDoctorDTO);
  }

  @GetMapping("/getAll")
  public ResponseEntity<?> getDoctorsAll() {
    List<Doctors> doctorList = doctorsService.getDoctorsAll();
    return new ResponseEntity<>(doctorList, HttpStatus.OK);
  }

  @GetMapping("getDoctorById/{id}")
  public ResponseEntity<?> getDoctorById(@PathVariable("id") int id) {
    return doctorsService.getDoctorByUserId(id);
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
    return doctorsService.updateDoctor(auxDoctorDTO);
  }
}
