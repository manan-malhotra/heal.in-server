package in.app.heal.service;

import in.app.heal.entities.AnxietyTest;
import in.app.heal.repository.AnxietyTestRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnxietyTestService {

  @Autowired private AnxietyTestRepository repository;

  public AnxietyTest addAnxietyTest(AnxietyTest anxietyTest) {
    repository.save(anxietyTest);
    return anxietyTest;
  }

  public List<AnxietyTest> getAnxietyTestAll() { return repository.findAll(); }

  public Optional<AnxietyTest> getAnxietyTestById(int id) {
    return repository.findById(id);
  }

  public void deleteAnxietyTestById(int id) { repository.deleteById(id); }

  public void deleteAllAnxietyTest() { repository.deleteAll(); }

  public void updateAnxietyTest(AnxietyTest anxietyTest) {
    repository.save(anxietyTest);
  }
}
