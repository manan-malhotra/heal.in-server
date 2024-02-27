package in.app.heal.service;

import in.app.heal.entities.SelfHelpVideos;
import in.app.heal.repository.SelfHelpVideosRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelfHelpVideosService {

  @Autowired private SelfHelpVideosRepository repository;

  public SelfHelpVideos addSelfHelpVideos(SelfHelpVideos selfHelpVideos) {
    repository.save(selfHelpVideos);
    return selfHelpVideos;
  }

  public List<SelfHelpVideos> getSelfHelpVideosAll() {
    return repository.findAll();
  }

  public Optional<SelfHelpVideos> getSelfHelpVideosById(int id) {
    return repository.findById(id);
  }

  public void deleteSelfHelpVideosById(int id) { repository.deleteById(id); }

  public void deleteAllSelfHelpVideos() { repository.deleteAll(); }

  public void updateSelfHelpVideos(SelfHelpVideos selfHelpVideos) {
    repository.save(selfHelpVideos);
  }
}
