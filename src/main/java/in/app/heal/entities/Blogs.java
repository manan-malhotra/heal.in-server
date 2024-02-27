package in.app.heal.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "blogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Blogs {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "blog_id")
  private Integer blog_id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id",
              nullable = false)
  private User user_id;

  @Column(name = "title", nullable = false) private String title;

  @Column(name = "description", nullable = false) private String description;

  @Column(name = "post_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date post_date;

  @OneToMany(mappedBy = "blog_id", cascade = CascadeType.ALL,
             orphanRemoval = true)
  private Set<FlaggedBlogs> flaggedBlogs = new HashSet<>();
}
