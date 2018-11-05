package tutorial.backend.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import tutorial.backend.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * Loads an entity with lazy property loaded from a database
     */
    @EntityGraph(attributePaths={"profilePicture"})
    User findWithPropertyPictureAttachedById(Long id);
}
