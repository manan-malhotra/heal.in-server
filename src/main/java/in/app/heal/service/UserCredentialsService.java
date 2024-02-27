package in.app.heal.service;

import in.app.heal.entities.UserCredentials;
import in.app.heal.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialsService {

    @Autowired
    private UserCredentialsRepository repository;

    public void addUser(UserCredentials userCredentials){
        repository.save(userCredentials);
    }
    public Optional<UserCredentials> findByEmail(String email){
        return Optional.ofNullable(repository.findByEmail(email));
    }

}
