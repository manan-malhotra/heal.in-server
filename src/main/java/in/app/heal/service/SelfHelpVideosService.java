package in.app.heal.service;
import in.app.heal.aux.AuxSelfHelpVideosDTO;
import in.app.heal.entities.SelfHelpVideos;
import in.app.heal.error.ApiError;
import in.app.heal.repository.SelfHelpVideosRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SelfHelpVideosService {

  @Autowired private SelfHelpVideosRepository repository;

  public ResponseEntity<?>
  addSelfHelpVideos(AuxSelfHelpVideosDTO auxSelfHelpVideosDTO) {
    SelfHelpVideos selfHelpVideos = new SelfHelpVideos();
    selfHelpVideos.setTitle(auxSelfHelpVideosDTO.getTitle());
    selfHelpVideos.setUrl(auxSelfHelpVideosDTO.getUrl());
    try {
      return new ResponseEntity<>(repository.save(selfHelpVideos),
                                  HttpStatus.OK);
    } catch (Exception e) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      apiError.setMessage(e.getMessage());
      return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public List<SelfHelpVideos> getSelfHelpVideosAll() {
    return repository.findAll();
  }

  public Optional<SelfHelpVideos> getSelfHelpVideosById(int id) {
    return repository.findById(id);
  }

  public ResponseEntity<?> deleteSelfHelpVideosById(int id) {
    ApiError apiError = new ApiError();
    if (repository.deleteById(id) == 1) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      apiError.setStatus(HttpStatus.NOT_FOUND);
      apiError.setMessage("Video not found");
      return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<?> deleteAllSelfHelpVideos() {
    repository.deleteAll();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?>
  updateSelfHelpVideos(AuxSelfHelpVideosDTO auxSelfHelpVideosDTO) {
    SelfHelpVideos selfHelpVideos = new SelfHelpVideos();
    selfHelpVideos.setId(auxSelfHelpVideosDTO.getId());
    selfHelpVideos.setTitle(auxSelfHelpVideosDTO.getTitle());
    selfHelpVideos.setUrl(auxSelfHelpVideosDTO.getUrl());
    try {
      if (repository.updateById(selfHelpVideos.getTitle(),
                                selfHelpVideos.getUrl(),
                                selfHelpVideos.getId()) == 1) {
        return new ResponseEntity<>(selfHelpVideos, HttpStatus.OK);
      } else {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setMessage("Video not found");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      apiError.setMessage(e.getMessage());
      return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
