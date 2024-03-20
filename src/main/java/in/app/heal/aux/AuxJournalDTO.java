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
    private Integer entryId;
    private String title;
    private String description;

}
