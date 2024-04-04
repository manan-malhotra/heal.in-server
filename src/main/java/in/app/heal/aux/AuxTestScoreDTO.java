package in.app.heal.aux;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuxTestScoreDTO {
    private int userId;
    private int testId;
    private int score;
    private int total;
    private Date submitDate;
}
