package in.app.heal.service;

import in.app.heal.aux.AuxEmailDTO;
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

    public String getEmail(AuxEmailDTO auxEmailDTO) {
        double percent = ((double) auxEmailDTO.getAuxTestScoreDTO().getScore() / auxEmailDTO.getAuxTestScoreDTO().getTotal()) * 100;
        String type = "";
        String userName = auxEmailDTO.getUsername();
        String testName = auxEmailDTO.getTestname();
        if (percent < 20) {
            type = "Minimal";
        } else if (percent < 40) {
            type = "Mild";
        } else if (percent < 60) {
            type = "Moderate";
        } else if (percent < 80) {
            type = "Moderately Severe";
        } else if (percent < 100) {
            type = "Severe";
        }

        String OverallPercent = String.format("%.2f", percent);

        senderService.sendSimpleEmail("somg0703@gmail.com",
                "Self Assessment Test Report",
                "Hello," + userName + "\n\n" + "Based on your responses, you may have symptoms of "+ type + " " + testName + ". This result is not a diagnosis, please consult a doctor or therapist who can help you get a diagnosed or treated.\n\n" +
                        "Overall Score:" + auxEmailDTO.getAuxTestScoreDTO().getScore()+"/"+auxEmailDTO.getAuxTestScoreDTO().getTotal()+ "\n" +
                        "Overall Percentage Score :" + OverallPercent + "\n\n" +
                        "Each of the option's scores are as follows:\n" +
                        "Not at all = 0\n" +
                        "Several days = 1\n" +
                        "More than half the days = 2\n" +
                        "Nearly every day = 3\n\n" +
                        "Interpreting your Total Percentage Score:\n" +
                        "0-20: Minimal " + testName + "\n" +
                        "21-40: Mild " + testName + "\n" +
                        "41-60: Moderate " + testName + "\n" +
                        "61-80: Moderately severe " + testName + "\n" +
                        "81-100: Severe " + testName + "\n");
        return "";
    }

    public List<TestScores> getAllScores(int userId){
        return testScoresRepository.getScores(userId);
    }
}
