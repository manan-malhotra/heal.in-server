package in.app.heal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tests")
public class Tests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Integer test_id;

    @Column(name = "title") private String test_name;

    @OneToMany(mappedBy = "test_id", cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<TestQuestions> testQuestions;

    @OneToMany(mappedBy = "score_id", cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<TestScores> testScores;
}