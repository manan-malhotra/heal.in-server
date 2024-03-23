package in.app.heal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
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
@Table(name = "public_qna")
public class PublicQNA {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "public_qna_id")
  private Integer public_qna_id;

  @Column(name = "question", nullable = false) private String question;

  @Column(name = "description", nullable = false) private String description;

  @Column(name = "added_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date added_date;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id",
              nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user_id;

  @OneToMany(mappedBy = "public_qna_id", cascade = CascadeType.ALL,
             orphanRemoval = true)
  private List<Comments> comments;

  @OneToMany(mappedBy = "public_qna_id", cascade = CascadeType.ALL,
             orphanRemoval = true)
  @JsonIgnore
  private List<FlaggedPublicQNA> flaggedPublicQNA;
}
