package in.app.heal.aux;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuxArticleDTO {
    private int id;
    private String title;
    private java.util.Date post_date;
}
