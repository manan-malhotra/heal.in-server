package in.app.heal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "userCredentials")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCredentials {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne()
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user_id;

  @Column(name = "email", nullable = false) private String email;

  @Column(name = "role", nullable = false) private String role;

  @Column(name = "password", nullable = false) private String password;
}
