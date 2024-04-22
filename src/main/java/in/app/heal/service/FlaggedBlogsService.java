package in.app.heal.service;

import in.app.heal.aux.AuxFlaggedBlogsDTO;
import in.app.heal.aux.FlagDTO;
import in.app.heal.entities.Blogs;
import in.app.heal.entities.FlaggedBlogs;
import in.app.heal.entities.User;
import in.app.heal.error.ApiError;
import in.app.heal.repository.FlaggedBlogsRepository;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FlaggedBlogsService {

  @Autowired private FlaggedBlogsRepository repository;

  @Autowired private UserService userService;

  @Autowired private BlogsService blogsService;

  public ResponseEntity<?>
  addFlaggedBlogs(AuxFlaggedBlogsDTO auxFlaggedBlogsDTO) {
    FlaggedBlogs flaggedBlogs = new FlaggedBlogs();
    Optional<Blogs> blog =
        blogsService.getBlogsById(auxFlaggedBlogsDTO.getBlog_id());
    if (blog.isPresent()) {
      flaggedBlogs.setBlog_id(blog.get());

    } else {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("Blog not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    Optional<User> user =
        userService.fetchById(auxFlaggedBlogsDTO.getUser_id());
    if (user.isPresent()) {
      flaggedBlogs.setUser_id(user.get());
    } else {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("User not found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    List<FlaggedBlogs> existingBlogs =
        this.getFlaggedBlogsByBlogId(blog.get().getBlog_id());
    for (int i = 0; i < existingBlogs.size(); i++) {
      if (Objects.equals(existingBlogs.get(i).getUser_id().getUser_id(),
                         auxFlaggedBlogsDTO.getUser_id())) {
        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
      }
    }

    flaggedBlogs.setReason(auxFlaggedBlogsDTO.getReason());
    flaggedBlogs.setFlagged_date(new Date());
    try {
      repository.save(flaggedBlogs);
      return new ResponseEntity<>(flaggedBlogs, HttpStatus.OK);
    } catch (Exception e) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      apiError.setMessage(e.getMessage());
      return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public List<FlagDTO> getFlaggedBlogsAll() {
    List<FlaggedBlogs> blogs = repository.findAll();
    List<FlagDTO> unique = new ArrayList<>();
    Set<Integer> uniqueBlog = new HashSet<>();
    for (int i = 0; i < blogs.size(); i++) {
      FlagDTO flag = new FlagDTO();
      flag.setId(blogs.get(i).getBlog_id().getBlog_id());
      flag.setAuthor(blogs.get(i).getBlog_id().getUser_id().getFirst_name() +
                     " " +
                     blogs.get(i).getBlog_id().getUser_id().getLast_name());
      flag.setTitle(blogs.get(i).getBlog_id().getTitle());
      flag.setDescription(blogs.get(i).getBlog_id().getDescription());
      if (!uniqueBlog.contains(flag.getId())) {
        uniqueBlog.add(flag.getId());
        unique.add(0, flag);
      }
      for (int j = 0; j < unique.size(); j++) {
        if (unique.get(j).getId() == flag.getId()) {
          String reason = blogs.get(i).getReason();
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

  public Optional<FlaggedBlogs> getFlaggedBlogsById(int id) {
    return repository.findById(id);
  }

  public List<FlaggedBlogs> getFlaggedBlogsByBlogId(int blogId) {
    return repository.findByBlogId(blogId);
  }

  public ResponseEntity<?> getFlaggedBlogsByBId(int blogId) {
    if (this.getFlaggedBlogsByBlogId(blogId).size() == 0) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("No flagged blogs found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>(this.getFlaggedBlogsByBlogId(blogId),
                                HttpStatus.OK);
  }

  public List<FlaggedBlogs> getFlaggedBlogsByUserId(int userId) {
    return repository.findByUserId(userId);
  }

  public ResponseEntity<?> getFlaggedBlogsByUId(int userId) {
    if (this.getFlaggedBlogsByUserId(userId).size() == 0) {
      ApiError apiError = new ApiError();
      apiError.setStatus(HttpStatus.CONFLICT);
      apiError.setMessage("No flagged blogs found");
      return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
    return new ResponseEntity<>(this.getFlaggedBlogsByUserId(userId),
                                HttpStatus.OK);
  }
}
