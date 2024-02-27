package in.app.heal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "anxiety_test")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnxietyTest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column(name = "question") private String question;

  @Column(name = "option_1") private String option1;

  @Column(name = "option_2") private String option2;

  @Column(name = "option_3") private String option3;

  @Column(name = "option_4") private String option4;
}
