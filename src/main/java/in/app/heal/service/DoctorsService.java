package in.app.heal.service;

import in.app.heal.entities.Doctors;
import in.app.heal.repository.DoctorsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorsService {

  @Autowired private DoctorsRepository repository;

  public List<Doctors> getDoctorsAll() { return repository.findAll(); }

  public Optional<Doctors> getDoctorById(int id) {
    return repository.findById(id);
  }

  public List<Doctors> getDoctorByUserId(int userId) {
    return repository.findByUserId(userId);
  }

  public List<Doctors> getDoctorBySpeciality(String speciality) {
    return repository.findBySpeciality(speciality);
  }

  public void addDoctor(Doctors doctor) { repository.save(doctor); }

  public void deleteDoctor(int id) { repository.deleteById(id); }

  public void deleteAll() { repository.deleteAll(); }

  public void updateDoctor(Doctors doctor) { repository.save(doctor); }
}
