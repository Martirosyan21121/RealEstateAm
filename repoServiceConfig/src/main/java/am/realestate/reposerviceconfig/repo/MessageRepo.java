package am.realestate.reposerviceconfig.repo;

import am.realestate.module.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Long> {

    Optional<Message> findMessageByForTo(String email);
}
