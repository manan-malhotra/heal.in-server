package in.app.heal.service;

import in.app.heal.aux.AuxArticleDTO;
import in.app.heal.aux.AuxBlogsDTO;
import in.app.heal.entities.Blogs;
import in.app.heal.entities.User;
import in.app.heal.error.ApiError;
import in.app.heal.repository.BlogsRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BlogsService {

  @Autowired private BlogsRepository repository;

  @Autowired private UserService userService;

  public ResponseEntity<?> addBlogs(AuxBlogsDTO auxBlogsDTO) {
    Blogs blogs = new Blogs();
    blogs.setTitle(auxBlogsDTO.getTitle());
    blogs.setDescription(auxBlogsDTO.getDescription());
    blogs.setPost_date(new Date());
    Optional<User> user = userService.fetchById(auxBlogsDTO.getUser_id());
    if (user.isPresent()) {
      blogs.setUser_id(user.get());
    } else {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("User not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    try {
      repository.save(blogs);

    } catch (Exception e) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      apiError.setMessage(e.getMessage());
      return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(auxBlogsDTO, HttpStatus.OK);
  }

  public List<AuxArticleDTO> getBlogsAll() {
    List<Blogs> blogs = repository.findAll();
    List<AuxArticleDTO> allBlogs = new ArrayList<>();
    for (int i = 0; i < blogs.size(); i++) {
      AuxArticleDTO auxArticleDTO = new AuxArticleDTO();
      auxArticleDTO.setId(blogs.get(i).getBlog_id());
      auxArticleDTO.setTitle(blogs.get(i).getTitle());
      auxArticleDTO.setPost_date(blogs.get(i).getPost_date());
      allBlogs.add(0, auxArticleDTO);
    }
    return allBlogs;
  }

  public Optional<Blogs> getBlogsById(int id) {
    return repository.findById(id);
  }

  public ResponseEntity<?> getBlog(int id) {
    Optional<Blogs> blog = repository.findById(id);
    if (blog.isPresent()) {
      return new ResponseEntity<Blogs>(blog.get(), HttpStatus.OK);
    } else {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("Blog not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
  }

  public Optional<Blogs> fetchById(int id) {
    Optional<Blogs> blog = repository.findById(id);
    return blog;
  }

  public ResponseEntity<?> deleteBlogsById(int id) {
    if (repository.findById(id).isPresent()) {
      repository.deleteById(id);
    } else {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("Blog not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> deleteAllBlogs() {
    try {
      repository.deleteAll();
    } catch (Exception e) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      apiError.setMessage(e.getMessage());
      return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public ResponseEntity<?> updateBlogs(AuxBlogsDTO auxBlogsDTO) {
    Blogs blogs = new Blogs();
    blogs.setBlog_id(auxBlogsDTO.getBlog_id());
    blogs.setTitle(auxBlogsDTO.getTitle());
    blogs.setDescription(auxBlogsDTO.getDescription());
    blogs.setPost_date(auxBlogsDTO.getPost_date());
    Optional<User> user = userService.fetchById(auxBlogsDTO.getUser_id());
    if (user.isPresent()) {
      blogs.setUser_id(user.get());
    } else {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    try {
      if (repository.updateById(auxBlogsDTO.getTitle(),
                                auxBlogsDTO.getDescription(),
                                auxBlogsDTO.getBlog_id()) == 0) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.CONFLICT);
        apiError.setMessage("Blog not found");
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
      } else {
        return new ResponseEntity<>(blogs, HttpStatus.OK);
      }
    } catch (Exception e) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      apiError.setMessage(e.getMessage());
      return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
