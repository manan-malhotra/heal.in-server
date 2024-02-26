package in.app.heal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Integer comment_id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id",
              nullable = false)
  private User user_id;

  @ManyToOne
  @JoinColumn(name = "blog_id", referencedColumnName = "blog_id",
              nullable = false)
  private Blogs blog_id;

  @Column(name = "comment", nullable = false) private String comment;
  @Column(name = "comment_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date comment_date;
}