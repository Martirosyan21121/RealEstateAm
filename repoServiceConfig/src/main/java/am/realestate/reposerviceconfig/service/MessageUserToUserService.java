package am.realestate.reposerviceconfig.service;

import am.realestate.module.MessageUserToUser;
import am.realestate.module.User;
import am.realestate.reposerviceconfig.repo.MessageUserToUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageUserToUserService {

    @Autowired
    private MessageUserToUserRepo messageUserToUserRepo;
    public void saveMessageUsers(MessageUserToUser messageUserToUser){
        messageUserToUserRepo.save(messageUserToUser);
    }

   public List<MessageUserToUser> findMessageByUserID(User user){
        return messageUserToUserRepo.findMessageUserToUserByUserId(user);
    }

   public List<MessageUserToUser> findMessageByForTo(String email){
        return messageUserToUserRepo.findMessageUserToUserByForTo(email);
    }

    public void deleteMessage(Long id) {
        messageUserToUserRepo.deleteById(id);
    }
}
