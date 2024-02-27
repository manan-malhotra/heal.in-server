package in.app.heal.service;

import in.app.heal.entities.ADHDTest;
import in.app.heal.repository.ADHDTestRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ADHDTestService {

  @Autowired private ADHDTestRepository repository;

  public ADHDTest addADHDTest(ADHDTest adhdTest) {
    repository.save(adhdTest);
    return adhdTest;
  }

  public List<ADHDTest> getADHDTestAll() { return repository.findAll(); }

  public Optional<ADHDTest> getADHDTestById(int id) {
    return repository.findById(id);
  }

  public void deleteADHDTestById(int id) { repository.deleteById(id); }

  public void deleteAllADHDTest() { repository.deleteAll(); }

  public void updateADHDTest(ADHDTest adhdTest) { repository.save(adhdTest); }
}
