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
public class AuxJournalDTO {

    private String title;
    private String description;
    private String tags;
    private String firstName;

}
