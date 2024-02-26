package in.app.heal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "journal_entry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "entry_id")
  private Integer entry_id;

  @ManyToOne()
  @JoinColumn(name = "user_id", referencedColumnName = "user_id",
              nullable = false)
  private User user_id;

  @Column(name = "title", nullable = false) private String title;
  @Column(name = "description", nullable = false) private String description;
  @Column(name = "entry_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date entry_date;
  @Column(name = "tags", nullable = false) private String tags;
}
