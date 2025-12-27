package hu.hm.fitjourneyapi.repository;

import hu.hm.fitjourneyapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByName(String name);

    List<User> findAllByNameContainingIgnoreCase(String name);
    
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(UUID id);

    User getUserById(UUID id);
}
