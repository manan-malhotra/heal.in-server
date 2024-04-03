package in.app.heal.service;

import in.app.heal.aux.FlagDTO;
import in.app.heal.entities.FlaggedPublicQNA;
import in.app.heal.repository.FlaggedPublicQNARepository;

import java.util.*;

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

  public List<FlagDTO> findAll() {
    List<FlaggedPublicQNA> qna = repository.findAll();
    List<FlagDTO> unique = new ArrayList<>();
    Set<Integer> uniqueBlog = new HashSet<>();
    for (int i = 0; i < qna.size(); i++) {
      FlagDTO flag = new FlagDTO();
      flag.setId(qna.get(i).getPublic_qna_id().getPublic_qna_id());
      flag.setAuthor(qna.get(i).getPublic_qna_id().getUser_id().getFirst_name()+" "+qna.get(i).getPublic_qna_id().getUser_id().getLast_name());
      flag.setTitle(qna.get(i).getPublic_qna_id().getQuestion());
      flag.setDescription("");
      if(!uniqueBlog.contains(flag.getId())){
        uniqueBlog.add(flag.getId());
        unique.add(0,flag);
      }
        for (int j = 0; j < unique.size(); j++) {
          if(unique.get(j).getId()== flag.getId()){
            String reason = qna.get(i).getReason();
            if(reason.toLowerCase().contains("hate")){
              unique.get(j).setHateCount(unique.get(j).getHateCount()+1);
            }
            else if(reason.toLowerCase().contains("spam")){
              unique.get(j).setSpamCount(unique.get(j).getSpamCount()+1);
            }
            else if(reason.toLowerCase().contains("irrelevancy")){
              unique.get(j).setIrrelevantCount(unique.get(j).getIrrelevantCount()+1);
            }
            else {
              unique.get(j).setOtherCount(unique.get(j).getOtherCount()+1);
            }
          }
        }
    }

    return unique;
  }

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
