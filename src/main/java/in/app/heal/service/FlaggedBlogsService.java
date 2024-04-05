package in.app.heal.service;

import in.app.heal.aux.FlagDTO;
import in.app.heal.entities.FlaggedBlogs;
import in.app.heal.repository.FlaggedBlogsRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlaggedBlogsService {

  @Autowired private FlaggedBlogsRepository repository;

  public FlaggedBlogs addFlaggedBlogs(FlaggedBlogs flaggedBlogs) {
    repository.save(flaggedBlogs);
    return flaggedBlogs;
  }

  public List<FlagDTO> getFlaggedBlogsAll() {
    List<FlaggedBlogs> blogs =repository.findAll();
    List<FlagDTO> unique = new ArrayList<>();
    Set<Integer> uniqueBlog = new HashSet<>();
    for (int i = 0; i < blogs.size(); i++) {
      FlagDTO flag = new FlagDTO();
      flag.setId(blogs.get(i).getBlog_id().getBlog_id());
      flag.setAuthor(blogs.get(i).getBlog_id().getUser_id().getFirst_name()+" "+blogs.get(i).getBlog_id().getUser_id().getLast_name());
      flag.setTitle(blogs.get(i).getBlog_id().getTitle());
      flag.setDescription(blogs.get(i).getBlog_id().getDescription());
      if(!uniqueBlog.contains(flag.getId())){
        uniqueBlog.add(flag.getId());
        unique.add(0,flag);
      }
        for (int j = 0; j < unique.size(); j++) {
          if(unique.get(j).getId()== flag.getId()){
            String reason = blogs.get(i).getReason();
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

  public Optional<FlaggedBlogs> getFlaggedBlogsById(int id) {
    return repository.findById(id);
  }

  public void deleteFlaggedBlogsById(int id) { repository.deleteById(id); }

  public void deleteAllFlaggedBlogs() { repository.deleteAll(); }

  public List<FlaggedBlogs> getFlaggedBlogsByBlogId(int blogId) {
    return repository.findByBlogId(blogId);
  }

  public List<FlaggedBlogs> getFlaggedBlogsByUserId(int userId) {
    return repository.findByUserId(userId);
  }
}
