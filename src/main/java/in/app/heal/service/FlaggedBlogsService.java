package in.app.heal.service;

import in.app.heal.entities.FlaggedBlogs;
import in.app.heal.repository.FlaggedBlogsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlaggedBlogsService {

  @Autowired private FlaggedBlogsRepository repository;

  public FlaggedBlogs addFlaggedBlogs(FlaggedBlogs flaggedBlogs) {
    repository.save(flaggedBlogs);
    return flaggedBlogs;
  }

  public List<FlaggedBlogs> getFlaggedBlogsAll() {
    return repository.findAll();
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
