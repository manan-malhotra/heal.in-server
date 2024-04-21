package in.app.heal.repository;

import in.app.heal.entities.Doctors;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DoctorsRepository extends JpaRepository<Doctors, Integer> {

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM doctors where user_id = ?1", nativeQuery = true)
  void deleteById(int id);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM doctors", nativeQuery = true)
  void deleteAll();

  List<Doctors> findAll();

  @Query(value = "SELECT * FROM doctors where user_id = ?1", nativeQuery = true)
  List<Doctors> findByUserId(int userId);

  @Query(value = "SELECT * FROM doctors where speciality = ?1",
         nativeQuery = true)
  List<Doctors>
  findBySpeciality(String speciality);
}
