package in.app.heal.service;

import in.app.heal.aux.LoginDTO;
import in.app.heal.entities.UserCredentials;
import in.app.heal.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import java.util.Optional;

@Service
public class UserCredentialsService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserCredentialsRepository repository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void addUser(UserCredentials userCredentials){
        repository.save(userCredentials);
    }
    public Optional<UserCredentials> findByEmail(String email){
        return Optional.ofNullable(repository.findByEmail(email));
    }

    public ResponseEntity<?> loginUser(LoginDTO loginDTO) {
        Optional<UserCredentials> userCredentials = this.findByEmail(loginDTO.getEmail());
        if (userCredentials.isPresent()) {
      UserCredentials userCredentialsfound = userCredentials.get();
      String password = userCredentialsfound.getPassword();
      boolean match = passwordEncoder.matches(loginDTO.getPassword(), password);
      if (match) {
        String jwtToken = tokenService.generateToken(loginDTO.getEmail());
        return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
