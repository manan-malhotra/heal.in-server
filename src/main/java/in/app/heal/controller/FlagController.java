package in.app.heal.controller;

import in.app.heal.aux.AuxFlaggedBlogsDTO;
import in.app.heal.aux.AuxFlaggedPublicQNADTO;
import in.app.heal.entities.Blogs;
import in.app.heal.entities.FlaggedBlogs;
import in.app.heal.entities.FlaggedPublicQNA;
import in.app.heal.entities.PublicQNA;
import in.app.heal.entities.User;
import in.app.heal.service.BlogsService;
import in.app.heal.service.FlaggedBlogsService;
import in.app.heal.service.FlaggedPublicQNAService;
import in.app.heal.service.PublicQNAService;
import in.app.heal.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flag")
public class FlagController {

  @Autowired private FlaggedBlogsService flaggedBlogsService;

  @Autowired private UserService userService;

  @Autowired private BlogsService blogsService;

  @Autowired private PublicQNAService publicQNAService;

  @Autowired private FlaggedPublicQNAService flaggedPublicQNAService;

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
    flaggedBlogs.setFlagged_date(new Date());
    flaggedBlogsService.addFlaggedBlogs(flaggedBlogs);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/blogs/deleteFlaggedBlogs/{id}")
  public ResponseEntity<?> deleteFlag(@PathVariable("id") int id) {
    flaggedBlogsService.deleteFlaggedBlogsById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/blogs/getAllFlaggedBlogs")
  public List<FlaggedBlogs> getAllFlaggedBlogs() {
    return flaggedBlogsService.getFlaggedBlogsAll();
  }

  @GetMapping("/blogs/getFlaggedBlogsByBlogId/{id}")
  public List<FlaggedBlogs>
  getFlaggedBlogsByBlogId(@PathVariable("id") int blogId) {
    return flaggedBlogsService.getFlaggedBlogsByBlogId(blogId);
  }

  @GetMapping("/blogs/getFlaggedBlogsByUserId/{id}")
  public List<FlaggedBlogs>
  getFlaggedBlogsByUserId(@PathVariable("id") int userId) {
    return flaggedBlogsService.getFlaggedBlogsByUserId(userId);
  }

  @PostMapping("/publicQNA/addFlaggedPublicQNA")
  public ResponseEntity<?>
  addFlag(@RequestBody AuxFlaggedPublicQNADTO auxFlaggedPublicQNADTO) {
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
    }
    flaggedPublicQNA.setReason(auxFlaggedPublicQNADTO.getReason());
    flaggedPublicQNA.setFlagged_date(new Date());
    flaggedPublicQNAService.addFlaggedPublicQNA(flaggedPublicQNA);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/publicQNA/deleteFlaggedPublicQNA/{id}")
  public ResponseEntity<?>
  deleteFlaggedPublicQNAById(@PathVariable("id") int id) {
    flaggedPublicQNAService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/publicQNA/getAllFlaggedPublicQNA")
  public List<FlaggedPublicQNA> getAllFlaggedPublicQNA() {
    return flaggedPublicQNAService.findAll();
  }

  @GetMapping("/publicQNA/getFlaggedPublicQNAByPublicQNAId/{id}")
  public List<FlaggedPublicQNA>
  getFlaggedPublicQNAByPublicQNAId(@PathVariable("id") int publicQNAId) {
    return flaggedPublicQNAService.getFlaggedByPublicQNAId(publicQNAId);
  }

  @GetMapping("/publicQNA/getFlaggedPublicQNAByUserId/{id}")
  public List<FlaggedPublicQNA>
  getFlaggedPublicQNAByUserId(@PathVariable("id") int userId) {
    return flaggedPublicQNAService.getFlaggedByUserId(userId);
  }
}
