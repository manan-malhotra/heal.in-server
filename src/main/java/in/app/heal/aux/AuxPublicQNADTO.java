package in.app.heal.aux;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuxPublicQNADTO {
  private Integer id;
  private String question;
  private String description;
  private Integer userId;
}
