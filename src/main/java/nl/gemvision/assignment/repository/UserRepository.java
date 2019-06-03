package nl.gemvision.assignment.repository;

import java.util.Optional;
import nl.gemvision.assignment.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findByUsername(String username);

  void deleteByUsername(String username);

}
