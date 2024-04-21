package in.app.heal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Integer user_id;

  @Column(name = "first_name", nullable = false) private String first_name;

  @Column(name = "last_name", nullable = false) private String last_name;

  @Column(name = "contact_number", nullable = false)
  private Long contact_number;

  @Column(name = "age", nullable = false) private Integer age;

  @Column(name = "gender", nullable = false)
  private String gender;

  @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL,
             orphanRemoval = true)
  @JsonIgnore
  private List<JournalEntry> journalEntries;

  @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL,
             orphanRemoval = true)
  @JsonIgnore
  private List<Blogs> blogs;

  @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL,
             orphanRemoval = true)
  @JsonIgnore
  private List<Comments> comments;

  @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL,
             orphanRemoval = true)
  @JsonIgnore
  private List<FlaggedPublicQNA> flaggedPublicQNA;

  @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL,
             orphanRemoval = true)
  @JsonIgnore
  private List<FlaggedBlogs> flaggedBlogs;

  @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL,
             orphanRemoval = true)
  @JsonIgnore
  private List<PublicQNA> publicQNA;
}

