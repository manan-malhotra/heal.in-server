package in.app.heal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user_id;

  @Column(name = "title", nullable = false) private String title;

  @Column(name = "description", nullable = false, length = 3000) private String description;

  @Column(name = "post_date", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date post_date;

  @OneToMany(mappedBy = "blog_id", cascade = CascadeType.ALL,
             orphanRemoval = true)
  @JsonIgnore
  private Set<FlaggedBlogs> flaggedBlogs = new HashSet<>();
}
