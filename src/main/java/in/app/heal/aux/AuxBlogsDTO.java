package in.app.heal.aux;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuxBlogsDTO {
  private Integer blog_id;
  private Integer user_id;
  private String title;
  private String description;
  private java.util.Date post_date;
}
