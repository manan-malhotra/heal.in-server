package in.app.heal.aux;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuxFlaggedBlogsDTO {
  private Integer id;
  private Integer user_id;
  private Integer blog_id;
  private String reason;
  private Date flagged_date;
}
