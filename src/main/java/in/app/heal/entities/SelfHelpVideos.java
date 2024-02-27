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
@Table(name = "self_help_videos")
public class SelfHelpVideos {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "title") private String title;

  @Column(name = "url") private String url;

  @Column(name = "description") private String description;

  @Column(name = "category") private String category;

  @Column(name = "tags") private String tags;

  @Column(name = "author") private String author;

  // @Column(name = "added_date")
  // @Temporal(TemporalType.TIMESTAMP)
  // private java.util.Date added_date;
}
