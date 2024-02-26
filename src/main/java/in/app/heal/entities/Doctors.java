package in.app.heal.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctors {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "doctor_id")
  private Integer doctor_id;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user_id;

  @Column(name = "specialization", nullable = false)
  private String specialization;

  @Column(name = "experience", nullable = false)
  private String experience;

  @Column(name = "degree", nullable = false)
  private String degree;

  @Column(name = "license_number", nullable = false)
  private String license_number;
}
