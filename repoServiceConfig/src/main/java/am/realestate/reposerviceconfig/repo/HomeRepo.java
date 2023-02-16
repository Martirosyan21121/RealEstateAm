package am.realestate.reposerviceconfig.repo;


import am.realestate.module.Home;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeRepo extends JpaRepository<Home, Long> {

    Optional<Home> findById(long id);
}
