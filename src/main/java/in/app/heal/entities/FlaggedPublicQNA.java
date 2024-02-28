package in.app.heal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flagged_public_qna")
public class FlaggedPublicQNA {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user_id;

  @ManyToOne
  @JoinColumn(name = "public_qna_id", referencedColumnName = "public_qna_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private PublicQNA public_qna_id;

  @Column(name = "reason", nullable = false) private String reason;

  @Column(name = "flagged_date", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date flagged_date;
}
