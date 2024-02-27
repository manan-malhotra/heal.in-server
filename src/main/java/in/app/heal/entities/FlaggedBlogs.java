package in.app.heal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flagged_blogs")
public class FlaggedBlogs {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user_id;

  @ManyToOne
  @JoinColumn(name = "blog_id", referencedColumnName = "blog_id")
  private Blogs blog_id;

  @Column(name = "reason", nullable = false) private String reason;

  @Column(name = "flagged_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date flagged_date;
}
