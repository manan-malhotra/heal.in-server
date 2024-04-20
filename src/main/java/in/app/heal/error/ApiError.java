package in.app.heal.error;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ApiError {
  private String message;
  private HttpStatus status;
}
