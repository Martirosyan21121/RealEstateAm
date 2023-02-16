package am.realestate.reposerviceconfig.service;

import am.realestate.module.Message;
import am.realestate.reposerviceconfig.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepo messageRepo;

    public void saveMessage(Message message){
        messageRepo.save(message);
    }

    public Optional<Message> findMessageByForTo(String email){
        return messageRepo.findMessageByForTo(email);
    }

}
