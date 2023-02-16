package am.realestate.reposerviceconfig.repo;

import am.realestate.module.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByToken(Integer token);
    Optional<User> findById(Long id);

}
