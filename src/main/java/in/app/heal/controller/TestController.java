package in.app.heal.controller;

import in.app.heal.aux.AuxEmailDTO;
import in.app.heal.aux.AuxTestDTO;
import in.app.heal.aux.AuxTestQuestion;
import in.app.heal.aux.AuxTestScoreDTO;
import in.app.heal.entities.TestQuestions;
import in.app.heal.entities.Tests;
import in.app.heal.service.EmailSenderService;
import in.app.heal.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService service;

    @Autowired
    EmailSenderService mailService;

    @PostMapping(path = "/add")
    public ResponseEntity<?>  addTest(@RequestBody AuxTestDTO auxTestDTO){
        service.addTests(auxTestDTO.getTestName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/getAll")
    public List<Tests> getAll(){
        return service.getAll();
    }

    @PostMapping(path = "/addQuestion")
    public ResponseEntity<?> addTestQuestion(@RequestBody AuxTestQuestion auxTestQuestion){
        service.addTestQuestion(auxTestQuestion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/getQuestions/{testId}")
    public List<TestQuestions> getQuestionsByTestId(@PathVariable Integer testId){
        return service.getQuestionsByTestId(testId);
    }

    @PostMapping(path = "/addScores")
    public ResponseEntity<?> addScores(@RequestBody AuxTestScoreDTO auxTestScoreDTO){
        service.addScores(auxTestScoreDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(path = "/getRecentScores/{userId}")
    public ResponseEntity<?> getRecentScores(@PathVariable int userId){
        return new ResponseEntity<>(service.getRecentScores(userId),HttpStatus.OK);

    }
    @GetMapping(path = "/getAllScores/{userId}")
    public ResponseEntity<?> getAllScores(@PathVariable int userId){
        return new ResponseEntity<>(service.getAllScores(userId),HttpStatus.OK);

    }
    @PostMapping(path = "/getEmail")
    public String getEmail(@RequestBody AuxEmailDTO auxEmailDTO){
        return mailService.getEmail(auxEmailDTO);
    }
}
