package in.app.heal.service;

import in.app.heal.entities.User;
import in.app.heal.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository repository;

  public User addUser(User user) {
    repository.save(user);
    return user;
  }
  
    public Optional<User> fetchByName(String firstName){
        Optional<User> user= repository.findByFirstName(firstName);
        return user;
    }

    public Optional<User> findById(Integer userId){
        return repository.findById(userId);
    }
  public Optional<User> fetchById(int id) {
    Optional<User> user = repository.findById(id);
    return user;
  }

}
