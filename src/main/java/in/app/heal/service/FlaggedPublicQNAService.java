package in.app.heal.service;

import in.app.heal.entities.FlaggedPublicQNA;
import in.app.heal.repository.FlaggedPublicQNARepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlaggedPublicQNAService {

  @Autowired private FlaggedPublicQNARepository repository;

  public Optional<FlaggedPublicQNA> findById(int id) {
    return repository.findById(id);
  }

  public void deleteById(int id) { repository.deleteById(id); }

  public void deleteAll() { repository.deleteAll(); }

  public void deleteByUserId(int userId) { repository.deleteByUserId(userId); }

  public void deleteByPublicQNAId(int publicQNAId) {
    repository.deleteByPublicQNAId(publicQNAId);
  }

  public List<FlaggedPublicQNA> findAll() { return repository.findAll(); }

  public void addFlaggedPublicQNA(FlaggedPublicQNA flaggedPublicQNA) {
    repository.save(flaggedPublicQNA);
  }

  public List<FlaggedPublicQNA> getFlaggedByUserId(int userId) {
    return repository.getFlaggedByUserId(userId);
  }

  public List<FlaggedPublicQNA> getFlaggedByPublicQNAId(int publicQNAId) {
    return repository.findByPublicQNAId(publicQNAId);
  }
}
