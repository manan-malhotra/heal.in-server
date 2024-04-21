package in.app.heal.service;

import in.app.heal.aux.AuxTestQuestion;
import in.app.heal.aux.AuxTestScoreDTO;
import in.app.heal.entities.TestQuestions;
import in.app.heal.entities.TestScores;
import in.app.heal.entities.Tests;
import in.app.heal.entities.User;
import in.app.heal.error.ApiError;
import in.app.heal.repository.TestQuestionsRepository;
import in.app.heal.repository.TestScoresRepository;
import in.app.heal.repository.TestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {
    @Autowired
    UserService userService;
    @Autowired
    TestsRepository testsRepository;
    @Autowired
    TestQuestionsRepository testQuestionsRepository;
    @Autowired
    TestScoresRepository testScoresRepository;
    @Autowired
    EmailSenderService senderService;
    public ResponseEntity<?> addTests(String testName){
        if(testName == null || testName.isEmpty()){
            ApiError apiError = new ApiError();
            apiError.setStatus(HttpStatus.BAD_REQUEST);
            apiError.setMessage("Test name cannot be null");
            return new ResponseEntity<Object>(apiError,HttpStatus.BAD_REQUEST); 
        }
        Optional<Tests> testOptional = testsRepository.findByTestName(testName);
        if(testOptional.isPresent()){
            ApiError apiError = new ApiError();
            apiError.setStatus(HttpStatus.CONFLICT);
            apiError.setMessage(testName+"Test already exists");
            return new ResponseEntity<Object>(apiError,HttpStatus.CONFLICT);
        }
        Tests test = new Tests();
        test.setTest_name(testName);
        return new ResponseEntity<>(testsRepository.save(test),HttpStatus.OK);        
    }

    public List<Tests> getAll(){
        return testsRepository.findAll();
    }
    public Optional<Tests> get(Integer testId){
        return testsRepository.findById(testId);
    }

    public ResponseEntity<?> addTestQuestion(AuxTestQuestion auxTestQuestion) {
        TestQuestions testQuestion = new TestQuestions();
        Optional<Tests> tests = this.get(auxTestQuestion.getTestId());
        if(tests.isPresent()){
            testQuestion.setTest_id(tests.get());
            testQuestion.setQuestion(auxTestQuestion.getQuestion());
            testQuestion.setOption1(auxTestQuestion.getOption1());
            testQuestion.setOption2(auxTestQuestion.getOption2());
            testQuestion.setOption3(auxTestQuestion.getOption3());
            testQuestion.setOption4(auxTestQuestion.getOption4());
            testQuestionsRepository.save(testQuestion);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.CONFLICT);
        apiError.setMessage("Test not found");
        return new ResponseEntity<>(apiError,HttpStatus.CONFLICT);
    }

    public ResponseEntity<?> getQuestionsByTestId(Integer testId) {
        Optional<Tests> tests = this.get(testId);
        if(tests.isPresent()){
            return new ResponseEntity<>(testQuestionsRepository.findByTestId(testId),HttpStatus.OK);
        }
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.CONFLICT);
        apiError.setMessage("Test not found");
        return new ResponseEntity<>(apiError,HttpStatus.CONFLICT);
    }

    public ResponseEntity<?> addScores(AuxTestScoreDTO auxTestScoreDTO) {
        Optional<User> user = userService.fetchById(auxTestScoreDTO.getUserId());
        if(user.isPresent()){
            Optional<Tests> test = this.get(auxTestScoreDTO.getTestId());
            if(test.isPresent()){
                TestScores testScores = new TestScores();
                testScores.setTest_id(test.get());
                testScores.setUser_id(user.get());
                testScores.setScore(auxTestScoreDTO.getScore());
                testScores.setTotal(auxTestScoreDTO.getTotal());
                testScores.setSubmitted_date(new Date());
                return new ResponseEntity<>(testScoresRepository.save(testScores),HttpStatus.OK);
            }
            ApiError apiError = new ApiError();
            apiError.setStatus(HttpStatus.CONFLICT);
            apiError.setMessage("Test not found");
            return new ResponseEntity<>(apiError,HttpStatus.CONFLICT);
        }
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.CONFLICT);
        apiError.setMessage("User not found");
        return new ResponseEntity<>(apiError,HttpStatus.CONFLICT);
    }

    public List<TestScores> getRecentScores(int userId) {
        List<TestScores> list = this.getAllScores(userId);
        int testId = 0;
        List<TestScores> uniqueList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int newTestId = list.get(i).getTest_id().getTest_id();
            if(testId!=newTestId){
                uniqueList.add(list.get(i));
                testId = newTestId;
            }
        }
        return uniqueList;
    }

    public List<TestScores> getAllScores(int userId){
        return testScoresRepository.getScores(userId);
    }
}
