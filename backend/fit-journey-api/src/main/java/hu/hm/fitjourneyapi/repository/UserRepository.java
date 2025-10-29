package hu.hm.fitjourneyapi.repository;

import hu.hm.fitjourneyapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String name);
    
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(long id);

    User getUserById(long id);
}
