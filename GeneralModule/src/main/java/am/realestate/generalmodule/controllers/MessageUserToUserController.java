package am.realestate.generalmodule.controllers;

import am.realestate.module.MessageUserToUser;
import am.realestate.module.User;
import am.realestate.reposerviceconfig.service.MessageUserToUserService;
import am.realestate.reposerviceconfig.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class MessageUserToUserController {

    @Autowired
    private MessageUserToUserService messageUserToUserService;

    @Autowired
    private UserService userService;

    @PostMapping("/sendMessage")
    private String messageUserToUser(@ModelAttribute MessageUserToUser messageUserToUser, Principal principal, ModelMap modelMap) {
        Optional<User> findByEmail = userService.findByEmail(principal.getName());
        messageUserToUser.setUserId(findByEmail.get());
        log.info("message text = {}", messageUserToUser.getText());
        log.info("for to = {}", messageUserToUser.getForTo());
        messageUserToUserService.saveMessageUsers(messageUserToUser);
        List<MessageUserToUser> messagesUserToUser = messageUserToUserService.findMessageByUserID(findByEmail.get());
        modelMap.addAttribute("messages", messagesUserToUser);
        log.info("all messages by User ID = {}", messagesUserToUser);
        return "messages/userToUser";
    }

    @GetMapping("/messageUserToUser")
    private String messagesUserToUser(ModelMap modelMap, Principal principal) {
        Optional<User> findByEmail = userService.findByEmail(principal.getName());
        List<MessageUserToUser> messagesUserToUser = messageUserToUserService.findMessageByUserID(findByEmail.get());
        modelMap.addAttribute("messages", messagesUserToUser);
        log.info("all messages by User ID = {}", messagesUserToUser);
        return "messages/userToUser";
    }


    @GetMapping("/msgUserToUserBack")
    private String msgUserToUserBack(ModelMap modelMap, Principal principal) {

        List<MessageUserToUser> messageUserToUsers = messageUserToUserService.findMessageByForTo(principal.getName());
        System.out.println(messageUserToUsers);
        Optional<User> findByEmail = userService.findByEmail(principal.getName());
        User user = findByEmail.get();
        modelMap.addAttribute("userD", user);
        modelMap.addAttribute("message", messageUserToUsers);

        return "messages/userToUserBackContact";
    }

    @GetMapping("/delete/messageUserToUser")
    public String deleteUser(@RequestParam("id") Long id) {
        messageUserToUserService.deleteMessage(id);
        log.info("deleted Message id = " + id);
        return "redirect:/messageUserToUser";
    }
}
