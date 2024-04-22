package in.app.heal.controller;

import in.app.heal.aux.AuxFlaggedBlogsDTO;
import in.app.heal.aux.AuxFlaggedPublicQNADTO;
import in.app.heal.aux.FlagDTO;
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
import java.util.*;
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
    return flaggedBlogsService.addFlaggedBlogs(auxFlaggedBlogsDTO);
  }

  @GetMapping("/blogs/getAllFlaggedBlogs")
  public List<FlagDTO> getAllFlaggedBlogs() {
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
    return flaggedPublicQNAService.addFlaggedPublicQNA(auxFlaggedPublicQNADTO);
  }

  @GetMapping("/publicQNA/getAllFlaggedPublicQNA")
  public List<FlagDTO> getAllFlaggedPublicQNA() {
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
