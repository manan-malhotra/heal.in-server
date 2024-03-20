package in.app.heal.aux;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuxDoctorsDTO {
  private Integer doctor_id;
  private Integer user_id;
  private String specialization;
  private Integer experience;
  private String degree;
  private String license_number;
}