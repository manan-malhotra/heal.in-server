package in.app.heal.service;

import in.app.heal.entities.User;
import in.app.heal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User addUser(User user){
        repository.save(user);
        return user;
    }

}
