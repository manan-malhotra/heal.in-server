package in.app.heal.service;

import in.app.heal.aux.AuxFlaggedPublicQNADTO;
import in.app.heal.aux.FlagDTO;
import in.app.heal.entities.FlaggedPublicQNA;
import in.app.heal.entities.PublicQNA;
import in.app.heal.entities.User;
import in.app.heal.error.ApiError;
import in.app.heal.repository.FlaggedPublicQNARepository;
import in.app.heal.service.PublicQNAService;
import in.app.heal.service.UserService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FlaggedPublicQNAService {

  @Autowired private FlaggedPublicQNARepository repository;

  @Autowired private UserService userService;

  @Autowired private PublicQNAService publicQNAService;

  public Optional<FlaggedPublicQNA> findById(int id) {
    return repository.findById(id);
  }

  public List<FlagDTO> findAll() {
    List<FlaggedPublicQNA> qna = repository.findAll();
    List<FlagDTO> unique = new ArrayList<>();
    Set<Integer> uniqueBlog = new HashSet<>();
    for (int i = 0; i < qna.size(); i++) {
      FlagDTO flag = new FlagDTO();
      flag.setId(qna.get(i).getPublic_qna_id().getPublic_qna_id());
      flag.setAuthor(
          qna.get(i).getPublic_qna_id().getUser_id().getFirst_name() + " " +
          qna.get(i).getPublic_qna_id().getUser_id().getLast_name());
      flag.setTitle(qna.get(i).getPublic_qna_id().getQuestion());
      flag.setDescription("");
      if (!uniqueBlog.contains(flag.getId())) {
        uniqueBlog.add(flag.getId());
        unique.add(0, flag);
      }
      for (int j = 0; j < unique.size(); j++) {
        if (unique.get(j).getId() == flag.getId()) {
          String reason = qna.get(i).getReason();
          if (reason.toLowerCase().contains("hate")) {
            unique.get(j).setHateCount(unique.get(j).getHateCount() + 1);
          } else if (reason.toLowerCase().contains("spam")) {
            unique.get(j).setSpamCount(unique.get(j).getSpamCount() + 1);
          } else if (reason.toLowerCase().contains("irrelevancy")) {
            unique.get(j).setIrrelevantCount(
                unique.get(j).getIrrelevantCount() + 1);
          } else {
            unique.get(j).setOtherCount(unique.get(j).getOtherCount() + 1);
          }
        }
      }
    }

    return unique;
  }

  public ResponseEntity<?>
  addFlaggedPublicQNA(AuxFlaggedPublicQNADTO auxFlaggedPublicQNADTO) {
    FlaggedPublicQNA flaggedPublicQNA = new FlaggedPublicQNA();
    Optional<PublicQNA> publicQNA =
        publicQNAService.findById(auxFlaggedPublicQNADTO.getPublic_qna_id());
    if (publicQNA.isPresent()) {
      flaggedPublicQNA.setPublic_qna_id(publicQNA.get());
    }
    Optional<User> user =
        userService.fetchById(auxFlaggedPublicQNADTO.getUser_id());
    if (user.isPresent()) {
      flaggedPublicQNA.setUser_id(user.get());
    } else {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("User not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    if (publicQNAService.findById(auxFlaggedPublicQNADTO.getPublic_qna_id())
            .isPresent()) {
      List<FlaggedPublicQNA> existingQNA =
          this.getFlaggedByPublicQNAId(publicQNA.get().getPublic_qna_id());
      for (int i = 0; i < existingQNA.size(); i++) {
        if (Objects.equals(existingQNA.get(i).getUser_id().getUser_id(),
                           auxFlaggedPublicQNADTO.getUser_id())) {
          System.out.println(existingQNA.get(i).getUser_id().getUser_id());
          return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
      }
      flaggedPublicQNA.setReason(auxFlaggedPublicQNADTO.getReason());
      flaggedPublicQNA.setFlagged_date(new Date());

      try {
        repository.save(flaggedPublicQNA);
        return new ResponseEntity<List<FlaggedPublicQNA>>(existingQNA,
                                                          HttpStatus.OK);
      } catch (Exception e) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("Public QNA not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
  }

  public List<FlaggedPublicQNA> getFlaggedByUserId(int userId) {
    return repository.getFlaggedByUserId(userId);
  }

  public ResponseEntity<?> getFlaggedByUId(int userId) {
    Optional<User> user = userService.findById(userId);
    if (!user.isPresent()) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("User not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    List<FlaggedPublicQNA> flaggedPublicQNA = repository.findByUserId(userId);
    return new ResponseEntity<>(flaggedPublicQNA, HttpStatus.OK);
  }

  public List<FlaggedPublicQNA> getFlaggedByPublicQNAId(int publicQNAId) {
    return repository.findByPublicQNAId(publicQNAId);
  }

  public ResponseEntity<?> getFlaggedByPQNAId(int publicQNAId) {
    Optional<PublicQNA> publicQNA = publicQNAService.findById(publicQNAId);
    if (!publicQNA.isPresent()) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("Public QNA not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    List<FlaggedPublicQNA> flaggedPublicQNA =
        repository.findByPublicQNAId(publicQNAId);
    return new ResponseEntity<>(flaggedPublicQNA, HttpStatus.OK);
  }
}
