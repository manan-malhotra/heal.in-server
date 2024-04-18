package in.app.heal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import in.app.heal.aux.AuxEmailDTO;


@Service
public class EmailSenderService {
    @Autowired
    JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fromemail@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Send...");


    }

    public String getEmail(AuxEmailDTO auxEmailDTO) {
        double percent = ((double) auxEmailDTO.getAuxTestScoreDTO().getScore() / auxEmailDTO.getAuxTestScoreDTO().getTotal()) * 100;
        String userName = auxEmailDTO.getUsername();
        String testName = auxEmailDTO.getTestname();
        String email = auxEmailDTO.getEmail();
        String type = auxEmailDTO.getType();
        String OverallPercent = String.format("%.2f", percent);
        System.out.println("Mail: "+ email);
        System.out.println("Type: "+ type);
        sendSimpleEmail(email,
                "Self Assessment Test Report",
                "Hello, " + userName + "\n\n" + "Based on your responses, you may have symptoms of "+ type + " " + testName + ". This result is not a diagnosis, please consult a doctor or therapist who can help you get a diagnosed or treated.\n\n" +
                        "Overall Score: " + auxEmailDTO.getAuxTestScoreDTO().getScore()+"/"+auxEmailDTO.getAuxTestScoreDTO().getTotal()+ "\n" +
                        "Overall Percentage Score: " + OverallPercent + "\n\n" +
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

}
