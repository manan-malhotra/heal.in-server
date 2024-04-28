package in.app.heal.controller;

import in.app.heal.aux.AuxFlaggedBlogsDTO;
import in.app.heal.aux.AuxFlaggedPublicQNADTO;
import in.app.heal.aux.FlagDTO;
import in.app.heal.service.FlaggedBlogsService;
import in.app.heal.service.FlaggedPublicQNAService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flag")
public class FlagController {

  @Autowired private FlaggedBlogsService flaggedBlogsService;

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
  public ResponseEntity<?>
  getFlaggedBlogsByBlogId(@PathVariable("id") int blogId) {
    return flaggedBlogsService.getFlaggedBlogsByBId(blogId);
  }

  @GetMapping("/blogs/getFlaggedBlogsByUserId/{id}")
  public ResponseEntity<?>
  getFlaggedBlogsByUserId(@PathVariable("id") int userId) {
    return flaggedBlogsService.getFlaggedBlogsByUId(userId);
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
  public ResponseEntity<?>
  getFlaggedPublicQNAByPublicQNAId(@PathVariable("id") int publicQNAId) {
    return flaggedPublicQNAService.getFlaggedByPQNAId(publicQNAId);
  }

  @GetMapping("/publicQNA/getFlaggedPublicQNAByUserId/{id}")
  public ResponseEntity<?>
  getFlaggedPublicQNAByUserId(@PathVariable("id") int userId) {
    return flaggedPublicQNAService.getFlaggedByUId(userId);
  }
}
