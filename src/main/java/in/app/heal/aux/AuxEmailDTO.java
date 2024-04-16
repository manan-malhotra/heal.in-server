package in.app.heal.aux;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuxEmailDTO {
    private AuxTestScoreDTO auxTestScoreDTO;
    private String username;
    private String testname;
}
