package in.app.heal.aux;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuxCommentEditDTO {

    private String comment;
    private Integer questionId;
    private Integer userId;
    private Integer commentId;
}
