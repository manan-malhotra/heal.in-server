package in.app.heal.controller;

import in.app.heal.aux.AuxFlaggedBlogsDTO;
import in.app.heal.entities.Blogs;
import in.app.heal.entities.FlaggedBlogs;
import in.app.heal.entities.User;
import in.app.heal.service.BlogsService;
import in.app.heal.service.FlaggedBlogsService;
import in.app.heal.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flag")
public class FlagController {

  @Autowired private FlaggedBlogsService flaggedBlogsService;

  @Autowired private UserService userService;

  @Autowired private BlogsService blogsService;

  @PostMapping("/blogs/addFlaggedBlogs")
  public ResponseEntity<?>
  addFlag(@RequestBody AuxFlaggedBlogsDTO auxFlaggedBlogsDTO) {
    FlaggedBlogs flaggedBlogs = new FlaggedBlogs();
    Optional<Blogs> blog =
        blogsService.getBlogsById(auxFlaggedBlogsDTO.getBlog_id());
    if (blog.isPresent()) {
      flaggedBlogs.setBlog_id(blog.get());
    }
    Optional<User> user =
        userService.fetchById(auxFlaggedBlogsDTO.getUser_id());
    if (user.isPresent()) {
      flaggedBlogs.setUser_id(user.get());
    }
    flaggedBlogs.setReason(auxFlaggedBlogsDTO.getReason());
    flaggedBlogs.setFlagged_date(auxFlaggedBlogsDTO.getFlagged_date());
    flaggedBlogsService.addFlaggedBlogs(flaggedBlogs);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/blogs/delete")
  public ResponseEntity<?>
  deleteFlag(@RequestBody AuxFlaggedBlogsDTO auxFlaggedBlogsDTO) {
    flaggedBlogsService.deleteFlaggedBlogsById(auxFlaggedBlogsDTO.getId());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/blogs/getAllFlaggedBlogs")
  public List<FlaggedBlogs> getAllFlaggedBlogs() {
    return flaggedBlogsService.getFlaggedBlogsAll();
  }

  @GetMapping("/blogs/getFlaggedBlogsByBlogId")
  public List<FlaggedBlogs> getFlaggedBlogsByBlogId(int blogId) {
    return flaggedBlogsService.getFlaggedBlogsByBlogId(blogId);
  }

  @GetMapping("/blogs/getFlaggedBlogsByUserId")
  public List<FlaggedBlogs> getFlaggedBlogsByUserId(int userId) {
    return flaggedBlogsService.getFlaggedBlogsByUserId(userId);
  }
}
