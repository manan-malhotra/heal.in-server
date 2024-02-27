package in.app.heal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adhd_test")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ADHDTest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "question_1") private String question1;

  @Column(name = "question_2") private String question2;

  @Column(name = "question_3") private String question3;

  @Column(name = "question_4") private String question4;

  @Column(name = "question_5") private String question5;

  @Column(name = "question_6") private String question6;

  @Column(name = "question_7") private String question7;
}
