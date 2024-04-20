package in.app.heal.service;

import in.app.heal.aux.AuxUserDTO;
import in.app.heal.aux.LoginDTO;
import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import in.app.heal.error.ApiError;
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
    private UserService userService;
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
    public ResponseEntity<?> getProfileDetails(String auth){
        String token = tokenService.getToken(auth);
        try {
            String email = tokenService.getEmailFromToken(token);
            Optional<UserCredentials> userCredentialsOptional = this.findByEmail(email);
            if (userCredentialsOptional.isPresent()) {
                UserCredentials userCredentials = userCredentialsOptional.get();
                User user = userCredentials.getUser_id();
                AuxUserDTO auxUserDTO = new AuxUserDTO();
                auxUserDTO.setAge(user.getAge());
                auxUserDTO.setGender(user.getGender());
                auxUserDTO.setEmail(email);
                auxUserDTO.setRole(userCredentials.getRole());
                auxUserDTO.setContact(user.getContact_number());
                auxUserDTO.setFirstName(user.getFirst_name());
                auxUserDTO.setLastName(user.getLast_name());
                auxUserDTO.setUserId(user.getUser_id());
                return new ResponseEntity<AuxUserDTO>(auxUserDTO, HttpStatus.OK);
            }
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> registerUser(AuxUserDTO auxUserDTO) {
        Optional<UserCredentials> alreadyExisting = this.findByEmail(auxUserDTO.getEmail());
        if(alreadyExisting.isPresent()){
            System.out.println("User already exists");
            ApiError apiError = new ApiError();
            apiError.setMessage("User already exists");
            apiError.setStatus(HttpStatus.CONFLICT);
            return new ResponseEntity<Object>(apiError, HttpStatus.CONFLICT);
        }
        User user = userService.populateUser(auxUserDTO);
        UserCredentials newUserCredentials = new UserCredentials();
        newUserCredentials.setEmail(auxUserDTO.getEmail());
        newUserCredentials.setUser_id(user);
        String hash = passwordEncoder.encode(auxUserDTO.getPassword());
        newUserCredentials.setPassword(hash);
        newUserCredentials.setRole(auxUserDTO.getRole());
        this.addUser(newUserCredentials);
        String jwtToken = tokenService.generateToken(auxUserDTO.getEmail());
        return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
    }
}
