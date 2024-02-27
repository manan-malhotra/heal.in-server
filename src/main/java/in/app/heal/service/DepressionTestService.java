package in.app.heal.service;

import in.app.heal.entities.DepressionTest;
import in.app.heal.repository.DepressionTestRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepressionTestService {

  @Autowired private DepressionTestRepository depressionTestRepository;

  public DepressionTest addDepressionTest(DepressionTest depressionTest) {
    depressionTestRepository.save(depressionTest);
    return depressionTest;
  }

  public List<DepressionTest> getDepressionTestAll() {
    return depressionTestRepository.findAll();
  }

  public Optional<DepressionTest> getDepressionTestById(int id) {
    return depressionTestRepository.findById(id);
  }

  public void deleteDepressionTestById(int id) {
    depressionTestRepository.deleteById(id);
  }

  public void deleteAllDepressionTest() {
    depressionTestRepository.deleteAll();
  }

  public void updateDepressionTest(DepressionTest depressionTest) {
    depressionTestRepository.save(depressionTest);
  }
}
