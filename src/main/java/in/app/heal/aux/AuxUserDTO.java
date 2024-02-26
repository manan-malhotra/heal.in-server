package in.app.heal.aux;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuxUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private Long contact;

}
