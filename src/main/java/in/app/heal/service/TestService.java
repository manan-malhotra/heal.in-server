package in.app.heal.service;

import in.app.heal.aux.AuxTestQuestion;
import in.app.heal.aux.AuxTestScoreDTO;
import in.app.heal.entities.TestQuestions;
import in.app.heal.entities.TestScores;
import in.app.heal.entities.Tests;
import in.app.heal.entities.User;
import in.app.heal.repository.TestQuestionsRepository;
import in.app.heal.repository.TestScoresRepository;
import in.app.heal.repository.TestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Tests addTests(String testName){
        Tests test = new Tests();
        test.setTest_name(testName);
        return testsRepository.save(test);
    }

    public List<Tests> getAll(){
        return testsRepository.findAll();
    }
    public Optional<Tests> get(Integer testId){
        return testsRepository.findById(testId);
    }

    public void addTestQuestion(AuxTestQuestion auxTestQuestion) {
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
        }
    }

    public List<TestQuestions> getQuestionsByTestId(Integer testId) {
        Optional<Tests> tests = this.get(testId);
        if(tests.isPresent()){
            return testQuestionsRepository.findByTestId(testId);
        }
        return null;
    }

    public void addScores(AuxTestScoreDTO auxTestScoreDTO) {
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
                testScoresRepository.save(testScores);
            }
        }
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
