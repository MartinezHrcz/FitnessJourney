package hu.hm.fitjourneyapi.repository;

import hu.hm.fitjourneyapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUsersByName(String name);
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(int id);

    User getUserById(int id);
}
