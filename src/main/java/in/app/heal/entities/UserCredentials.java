package in.app.heal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user_id;

  @Column(name = "email", nullable = false,unique = true) private String email;

  @Column(name = "role", nullable = false) private String role;

  @Column(name = "password", nullable = false) private String password;
}
