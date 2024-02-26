package in.app.heal.service;

import in.app.heal.entities.User;
import in.app.heal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User addUser(User user){
        repository.save(user);
        return user;
    }

    public Optional<User> fetchByName(String firstName){
        Optional<User> user= repository.findByFirstName(firstName);
        return user;
    }
}
