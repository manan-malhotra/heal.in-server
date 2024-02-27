package in.app.heal.controller;

import in.app.heal.aux.AuxDepressionTestDTO;
import in.app.heal.entities.DepressionTest;
import in.app.heal.service.DepressionTestService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
  @Autowired private DepressionTestService depressionTestService;

  @PostMapping("/add-depression-test")
  public ResponseEntity<AuxDepressionTestDTO>
  addDepressionTest(@RequestBody AuxDepressionTestDTO auxDepressionTestDTO) {
    DepressionTest depressionTest = new DepressionTest();
    depressionTest.setQuestion(auxDepressionTestDTO.getQuestion());
    depressionTest.setOption1(auxDepressionTestDTO.getOption1());
    depressionTest.setOption2(auxDepressionTestDTO.getOption2());
    depressionTest.setOption3(auxDepressionTestDTO.getOption3());
    depressionTest.setOption4(auxDepressionTestDTO.getOption4());
    depressionTestService.addDepressionTest(depressionTest);
    return new ResponseEntity<>(auxDepressionTestDTO, HttpStatus.OK);
  }

  @PostMapping("/update-depression-test")
  public void
  updateDepressionTest(@RequestBody AuxDepressionTestDTO auxDepressionTestDTO) {
    DepressionTest depressionTest = new DepressionTest();
    depressionTest.setId(auxDepressionTestDTO.getId());
    depressionTest.setQuestion(auxDepressionTestDTO.getQuestion());
    depressionTest.setOption1(auxDepressionTestDTO.getOption1());
    depressionTest.setOption2(auxDepressionTestDTO.getOption2());
    depressionTest.setOption3(auxDepressionTestDTO.getOption3());
    depressionTest.setOption4(auxDepressionTestDTO.getOption4());
    depressionTestService.updateDepressionTest(depressionTest);
  }

  @DeleteMapping("/delete-depression-test/{id}")
  public void deleteDepressionTestById(@PathVariable("id") int id) {
    depressionTestService.deleteDepressionTestById(id);
  }

  @PostMapping("/delete-all-depression-test")
  public void deleteAllDepressionTest() {
    depressionTestService.deleteAllDepressionTest();
  }

  @PostMapping("/get-all-depression-test")
  public List<DepressionTest> getAllDepressionTest() {
    return depressionTestService.getDepressionTestAll();
  }
}
