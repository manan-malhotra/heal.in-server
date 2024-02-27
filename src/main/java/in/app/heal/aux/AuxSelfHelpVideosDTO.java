package in.app.heal.aux;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuxSelfHelpVideosDTO {
  private Integer id;
  private String title;
  private String description;
  private String tags;
  private String url;
  private String author;
  private String category;
}
