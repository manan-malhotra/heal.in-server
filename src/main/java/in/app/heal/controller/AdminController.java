package in.app.heal.controller;

import in.app.heal.aux.AuxTestDTO;
import in.app.heal.entities.ADHDTest;
import in.app.heal.entities.AnxietyTest;
import in.app.heal.entities.DepressionTest;
import in.app.heal.service.ADHDTestService;
import in.app.heal.service.AnxietyTestService;
import in.app.heal.service.DepressionTestService;
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

  @Autowired private AnxietyTestService anxietyTestService;

  @Autowired private ADHDTestService adhdTestService;

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
}
