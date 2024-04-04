package in.app.heal.controller;

import in.app.heal.aux.*;
import in.app.heal.entities.Blogs;
import in.app.heal.entities.SelfHelpVideos;
import in.app.heal.entities.User;
import in.app.heal.service.BlogsService;
import in.app.heal.service.SelfHelpVideosService;
import in.app.heal.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired private UserService userService;

  @Autowired private SelfHelpVideosService selfHelpVideosService;

  @Autowired private BlogsService blogsService;



  @GetMapping("/getAllSelfHelpVideos")
  public List<SelfHelpVideos> getAllSelfHelpVideos() {
    return selfHelpVideosService.getSelfHelpVideosAll();
  }

  @PostMapping("/addSelfHelpVideos")
  public ResponseEntity<AuxSelfHelpVideosDTO>
  addSelfHelpVideos(@RequestBody AuxSelfHelpVideosDTO auxSelfHelpVideosDTO) {
    SelfHelpVideos selfHelpVideos = new SelfHelpVideos();
    selfHelpVideos.setTitle(auxSelfHelpVideosDTO.getTitle());
    selfHelpVideos.setDescription(auxSelfHelpVideosDTO.getDescription());
    selfHelpVideos.setTags(auxSelfHelpVideosDTO.getTags());
    selfHelpVideos.setUrl(auxSelfHelpVideosDTO.getUrl());
    selfHelpVideos.setCategory(auxSelfHelpVideosDTO.getCategory());
    selfHelpVideos.setAuthor(auxSelfHelpVideosDTO.getAuthor());
    selfHelpVideosService.addSelfHelpVideos(selfHelpVideos);
    return new ResponseEntity<>(auxSelfHelpVideosDTO, HttpStatus.OK);
  }

  @PostMapping("/updateSelfHelpVideos")
  public void
  updateSelfHelpVideos(@RequestBody AuxSelfHelpVideosDTO auxSelfHelpVideosDTO) {
    SelfHelpVideos selfHelpVideos = new SelfHelpVideos();
    selfHelpVideos.setId(auxSelfHelpVideosDTO.getId());
    selfHelpVideos.setTitle(auxSelfHelpVideosDTO.getTitle());
    selfHelpVideos.setDescription(auxSelfHelpVideosDTO.getDescription());
    selfHelpVideos.setTags(auxSelfHelpVideosDTO.getTags());
    selfHelpVideos.setUrl(auxSelfHelpVideosDTO.getUrl());
    selfHelpVideos.setCategory(auxSelfHelpVideosDTO.getCategory());
    selfHelpVideos.setAuthor(auxSelfHelpVideosDTO.getAuthor());
    selfHelpVideosService.updateSelfHelpVideos(selfHelpVideos);
  }

  @DeleteMapping("/deleteSelfHelpVideos/{id}")
  public void deleteSelfHelpVideosById(@PathVariable("id") int id) {
    selfHelpVideosService.deleteSelfHelpVideosById(id);
  }

  @DeleteMapping("/deleteAllSelfHelpVideos")
  public void deleteAllSelfHelpVideos() {
    selfHelpVideosService.deleteAllSelfHelpVideos();
  }

  @GetMapping("/getAllBlogs")
  public List<AuxArticleDTO> getAllBlogs() {
    return blogsService.getBlogsAll();
  }

  @GetMapping("/getBlog/{id}")
  public ResponseEntity<?> getBlogById(@PathVariable("id") int id){
    Optional<Blogs> blogsOptional = blogsService.getBlogsById(id);
    if(blogsOptional.isPresent()){
      return new ResponseEntity<Blogs>(blogsOptional.get(),HttpStatus.OK);
    }else{
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  };

  @PostMapping("/addBlogs")
  public ResponseEntity<AuxBlogsDTO>
  addBlogs(@RequestBody AuxBlogsDTO auxBlogsDTO) {
    Blogs blogs = new Blogs();
    blogs.setTitle(auxBlogsDTO.getTitle());
    blogs.setDescription(auxBlogsDTO.getDescription());
    blogs.setPost_date(new Date());
    Optional<User> user = userService.fetchById(auxBlogsDTO.getUser_id());
    if (user.isPresent()) {
      blogs.setUser_id(user.get());
    }
    blogsService.addBlogs(blogs);
    return new ResponseEntity<>(auxBlogsDTO, HttpStatus.OK);
  }

  @PostMapping("/updateBlogs")
  public void updateBlogs(@RequestBody AuxBlogsDTO auxBlogsDTO) {
    Blogs blogs = new Blogs();
    blogs.setBlog_id(auxBlogsDTO.getBlog_id());
    blogs.setTitle(auxBlogsDTO.getTitle());
    blogs.setDescription(auxBlogsDTO.getDescription());
    blogs.setPost_date(auxBlogsDTO.getPost_date());
    // blogs.setUser_id(auxBlogsDTO.getUser_id());
    Optional<User> user = userService.fetchById(auxBlogsDTO.getUser_id());
    if (user.isPresent()) {
      blogs.setUser_id(user.get());
    }

    blogsService.updateBlogs(blogs);
  }

  @DeleteMapping("/deleteBlogs/{id}")
  public void deleteBlogsById(@PathVariable("id") int id) {
    blogsService.deleteBlogsById(id);
  }

  @DeleteMapping("/deleteAllBlogs")
  public void deleteAllBlogs() {
    blogsService.deleteAllBlogs();
  }
}
