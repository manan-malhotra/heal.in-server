package in.app.heal.repository;

import in.app.heal.entities.User;
import in.app.heal.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {
    public UserCredentials findByEmail(String email);
}
