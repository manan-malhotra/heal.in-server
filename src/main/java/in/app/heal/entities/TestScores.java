package in.app.heal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_scores")
public class TestScores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Integer score_id;

    @ManyToOne()
    @JoinColumn(name = "test_id", referencedColumnName = "test_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tests test_id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user_id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "total")
    private Integer total;

    @Column(name = "submitted_date")
    private Date submitted_date;

}
