package in.app.heal.service;

import in.app.heal.entities.Blogs;
import in.app.heal.repository.BlogsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogsService {

  @Autowired private BlogsRepository repository;

  public Blogs addBlogs(Blogs blogs) {
    repository.save(blogs);
    return blogs;
  }

  public List<Blogs> getBlogsAll() { return repository.findAll(); }

  public Optional<Blogs> getBlogsById(int id) {
    return repository.findById(id);
  }

  public Optional<Blogs> fetchById(int id) {
    Optional<Blogs> blog = repository.findById(id);
    return blog;
  }

  public void deleteBlogsById(int id) { repository.deleteById(id); }

  public void deleteAllBlogs() { repository.deleteAll(); }

  public void updateBlogs(Blogs blogs) { repository.save(blogs); }
}
