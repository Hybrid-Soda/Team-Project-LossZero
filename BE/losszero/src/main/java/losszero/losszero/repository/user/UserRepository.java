package losszero.losszero.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import losszero.losszero.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Boolean existsByUsername(String username);

    User findByUsername(String username);


}