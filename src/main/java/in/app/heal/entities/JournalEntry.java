package in.app.heal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
  @JsonIgnore
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user_id;

  @Column(name = "title", nullable = false) private String title;

  @Column(name = "description", nullable = false, length = 3000) private String description;

  @Column(name = "entry_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date entry_date;

}
