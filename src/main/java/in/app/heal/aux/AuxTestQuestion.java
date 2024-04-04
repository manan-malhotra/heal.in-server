package in.app.heal.aux;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuxTestQuestion {
    private Integer id;
    private Integer testId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

}
