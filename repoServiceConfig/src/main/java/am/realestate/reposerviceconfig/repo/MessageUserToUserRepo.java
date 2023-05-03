package am.realestate.reposerviceconfig.repo;

import am.realestate.module.MessageUserToUser;
import am.realestate.module.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageUserToUserRepo extends JpaRepository<MessageUserToUser, Long> {

 List<MessageUserToUser> findMessageUserToUserByUserId(User user);
 List<MessageUserToUser> findMessageUserToUserByForTo(String email);

}
