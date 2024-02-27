package in.app.heal.controller;

import in.app.heal.aux.AuxBlogsDTO;
import in.app.heal.aux.AuxSelfHelpVideosDTO;
import in.app.heal.aux.AuxTestDTO;
import in.app.heal.aux.AuxUserDTO;
import in.app.heal.entities.ADHDTest;
import in.app.heal.entities.AnxietyTest;
import in.app.heal.entities.Blogs;
import in.app.heal.entities.DepressionTest;
import in.app.heal.entities.SelfHelpVideos;
import in.app.heal.entities.User;
import in.app.heal.service.ADHDTestService;
import in.app.heal.service.AnxietyTestService;
import in.app.heal.service.BlogsService;
import in.app.heal.service.DepressionTestService;
import in.app.heal.service.SelfHelpVideosService;
import in.app.heal.service.UserService;
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
  @Autowired private DepressionTestService depressionTestService;

  @Autowired private UserService userService;

  @Autowired private AnxietyTestService anxietyTestService;

  @Autowired private ADHDTestService adhdTestService;

  @Autowired private SelfHelpVideosService selfHelpVideosService;

  @Autowired private BlogsService blogsService;

  @PostMapping("/addDepressionTest")
  public ResponseEntity<AuxTestDTO>
  addDepressionTest(@RequestBody AuxTestDTO auxDepressionTestDTO) {
    DepressionTest depressionTest = new DepressionTest();
    depressionTest.setQuestion(auxDepressionTestDTO.getQuestion());
    depressionTest.setOption1(auxDepressionTestDTO.getOption1());
    depressionTest.setOption2(auxDepressionTestDTO.getOption2());
    depressionTest.setOption3(auxDepressionTestDTO.getOption3());
    depressionTest.setOption4(auxDepressionTestDTO.getOption4());
    depressionTestService.addDepressionTest(depressionTest);
    return new ResponseEntity<>(auxDepressionTestDTO, HttpStatus.OK);
  }

  @PostMapping("/updateDepressionTest")
  public void
  updateDepressionTest(@RequestBody AuxTestDTO auxDepressionTestDTO) {
    DepressionTest depressionTest = new DepressionTest();
    depressionTest.setId(auxDepressionTestDTO.getId());
    depressionTest.setQuestion(auxDepressionTestDTO.getQuestion());
    depressionTest.setOption1(auxDepressionTestDTO.getOption1());
    depressionTest.setOption2(auxDepressionTestDTO.getOption2());
    depressionTest.setOption3(auxDepressionTestDTO.getOption3());
    depressionTest.setOption4(auxDepressionTestDTO.getOption4());
    depressionTestService.updateDepressionTest(depressionTest);
  }

  @DeleteMapping("/deleteDepressionTest/{id}")
  public void deleteDepressionTestById(@PathVariable("id") int id) {
    depressionTestService.deleteDepressionTestById(id);
  }

  @PostMapping("/deleteAllDepressionTest")
  public void deleteAllDepressionTest() {
    depressionTestService.deleteAllDepressionTest();
  }

  @GetMapping("/getAllDepressionTest")
  public List<DepressionTest> getAllDepressionTest() {
    return depressionTestService.getDepressionTestAll();
  }

  @PostMapping("/addAnxietyTest")
  public ResponseEntity<AuxTestDTO>
  addAnxietyTest(@RequestBody AuxTestDTO auxAnxietyTestDTO) {
    AnxietyTest anxietyTest = new AnxietyTest();
    anxietyTest.setQuestion(auxAnxietyTestDTO.getQuestion());
    anxietyTest.setOption1(auxAnxietyTestDTO.getOption1());
    anxietyTest.setOption2(auxAnxietyTestDTO.getOption2());
    anxietyTest.setOption3(auxAnxietyTestDTO.getOption3());
    anxietyTest.setOption4(auxAnxietyTestDTO.getOption4());
    anxietyTestService.addAnxietyTest(anxietyTest);
    return new ResponseEntity<>(auxAnxietyTestDTO, HttpStatus.OK);
  }

  @PostMapping("/updateAnxietyTest")
  public void updateAnxietyTest(@RequestBody AuxTestDTO auxAnxietyTestDTO) {
    AnxietyTest anxietyTest = new AnxietyTest();
    anxietyTest.setId(auxAnxietyTestDTO.getId());
    anxietyTest.setQuestion(auxAnxietyTestDTO.getQuestion());
    anxietyTest.setOption1(auxAnxietyTestDTO.getOption1());
    anxietyTest.setOption2(auxAnxietyTestDTO.getOption2());
    anxietyTest.setOption3(auxAnxietyTestDTO.getOption3());
    anxietyTest.setOption4(auxAnxietyTestDTO.getOption4());
    anxietyTestService.updateAnxietyTest(anxietyTest);
  }

  @DeleteMapping("/deleteAnxietyTest/{id}")
  public void deleteAnxietyTestById(@PathVariable("id") int id) {
    anxietyTestService.deleteAnxietyTestById(id);
  }

  @PostMapping("/deleteAllAnxietyTest")
  public void deleteAllAnxietyTest() {
    anxietyTestService.deleteAllAnxietyTest();
  }

  @GetMapping("/getAllAnxietyTest")
  public List<AnxietyTest> getAllAnxietyTest() {
    return anxietyTestService.getAnxietyTestAll();
  }

  @PostMapping("/addADHDTest")
  public ResponseEntity<AuxTestDTO>
  addADHDTest(@RequestBody AuxTestDTO auxADHDTestDTO) {
    ADHDTest adhdTest = new ADHDTest();
    adhdTest.setQuestion(auxADHDTestDTO.getQuestion());
    adhdTest.setOption1(auxADHDTestDTO.getOption1());
    adhdTest.setOption2(auxADHDTestDTO.getOption2());
    adhdTest.setOption3(auxADHDTestDTO.getOption3());
    adhdTest.setOption4(auxADHDTestDTO.getOption4());
    adhdTestService.addADHDTest(adhdTest);
    return new ResponseEntity<>(auxADHDTestDTO, HttpStatus.OK);
  }

  @PostMapping("/updateADHDTest")
  public void updateADHDTest(@RequestBody AuxTestDTO auxADHDTestDTO) {
    ADHDTest adhdTest = new ADHDTest();
    adhdTest.setId(auxADHDTestDTO.getId());
    adhdTest.setQuestion(auxADHDTestDTO.getQuestion());
    adhdTest.setOption1(auxADHDTestDTO.getOption1());
    adhdTest.setOption2(auxADHDTestDTO.getOption2());
    adhdTest.setOption3(auxADHDTestDTO.getOption3());
    adhdTest.setOption4(auxADHDTestDTO.getOption4());
    adhdTestService.updateADHDTest(adhdTest);
  }

  @DeleteMapping("/deleteADHDTest/{id}")
  public void deleteADHDTestById(@PathVariable("id") int id) {
    adhdTestService.deleteADHDTestById(id);
  }

  @PostMapping("/deleteAllADHDTest")
  public void deleteAllADHDTest() {
    adhdTestService.deleteAllADHDTest();
  }

  @GetMapping("/getAllADHDTest")
  public List<ADHDTest> getAllADHDTest() {
    return adhdTestService.getADHDTestAll();
  }

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
  public List<Blogs> getAllBlogs() {
    return blogsService.getBlogsAll();
  }

  @PostMapping("/addBlogs")
  public ResponseEntity<AuxBlogsDTO>
  addBlogs(@RequestBody AuxBlogsDTO auxBlogsDTO) {
    Blogs blogs = new Blogs();
    blogs.setTitle(auxBlogsDTO.getTitle());
    blogs.setDescription(auxBlogsDTO.getDescription());
    blogs.setPost_date(auxBlogsDTO.getPost_date());
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
