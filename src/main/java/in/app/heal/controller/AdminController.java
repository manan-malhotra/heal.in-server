package in.app.heal.controller;

import in.app.heal.aux.*;
import in.app.heal.entities.SelfHelpVideos;
import in.app.heal.service.BlogsService;
import in.app.heal.service.SelfHelpVideosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired private SelfHelpVideosService selfHelpVideosService;

  @Autowired private BlogsService blogsService;

  @GetMapping("/getAllSelfHelpVideos")
  public List<SelfHelpVideos> getAllSelfHelpVideos() {
    return selfHelpVideosService.getSelfHelpVideosAll();
  }

  @PostMapping("/addSelfHelpVideos")
  public ResponseEntity<?>
  addSelfHelpVideos(@RequestBody AuxSelfHelpVideosDTO auxSelfHelpVideosDTO) {
    return selfHelpVideosService.addSelfHelpVideos(auxSelfHelpVideosDTO);
  }

  @PostMapping("/updateSelfHelpVideos")
  public ResponseEntity<?>
  updateSelfHelpVideos(@RequestBody AuxSelfHelpVideosDTO auxSelfHelpVideosDTO) {
    return selfHelpVideosService.updateSelfHelpVideos(auxSelfHelpVideosDTO);
  }

  @DeleteMapping("/deleteSelfHelpVideos/{id}")
  public ResponseEntity<?>
  deleteSelfHelpVideosById(@PathVariable("id") int id) {
    return selfHelpVideosService.deleteSelfHelpVideosById(id);
  }

  @DeleteMapping("/deleteAllSelfHelpVideos")
  public ResponseEntity<?> deleteAllSelfHelpVideos() {
    return selfHelpVideosService.deleteAllSelfHelpVideos();
  }

  @GetMapping("/getAllBlogs")
  public List<AuxArticleDTO> getAllBlogs() {
    return blogsService.getBlogsAll();
  }

  @GetMapping("/getBlog/{id}")
  public ResponseEntity<?> getBlogById(@PathVariable("id") int id) {
    return blogsService.getBlog(id);
  };

  @PostMapping("/addBlogs")
  public ResponseEntity<?> addBlogs(@RequestBody AuxBlogsDTO auxBlogsDTO) {
    return blogsService.addBlogs(auxBlogsDTO);
  }

  @PostMapping("/updateBlogs")
  public ResponseEntity<?> updateBlogs(@RequestBody AuxBlogsDTO auxBlogsDTO) {
    return blogsService.updateBlogs(auxBlogsDTO);
  }

  @DeleteMapping("/deleteBlogs/{id}")
  public ResponseEntity<?> deleteBlogsById(@PathVariable("id") int id) {
    return blogsService.deleteBlogsById(id);
  }

  @DeleteMapping("/deleteAllBlogs")
  public ResponseEntity<?> deleteAllBlogs() {
    return blogsService.deleteAllBlogs();
  }
}
