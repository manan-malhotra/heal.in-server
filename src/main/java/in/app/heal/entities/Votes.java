package in.app.heal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Votes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "vote_id")
  private Integer vote_id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id",
              nullable = false)
  private User user_id;

  @ManyToOne
  @JoinColumn(name = "blog_id", referencedColumnName = "blog_id",
              nullable = false)
  private Blogs blog_id;

  @Enumerated(EnumType.STRING)
  @Column(name = "vote_type")
  private VoteType vote_type;
}

enum VoteType { UPVOTE, DOWNVOTE }
