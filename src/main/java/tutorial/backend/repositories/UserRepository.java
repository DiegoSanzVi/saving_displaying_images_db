package tutorial.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tutorial.backend.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
