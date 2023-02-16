package am.realestate.generalmodule.controllers;

import am.realestate.module.Message;
import am.realestate.module.User;
import am.realestate.reposerviceconfig.repo.MessageRepo;
import am.realestate.reposerviceconfig.service.MessageService;
import am.realestate.reposerviceconfig.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.web.servlet.oauth2.resourceserver.OpaqueTokenDsl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class MessageController {

    public static LocalTime localTime = LocalTime.now();

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    public UserService userService;

    @PostMapping("/sendMessage")
    private String sendMessage(@ModelAttribute Message message, Principal principal, ModelMap modelMap, User users) {
        Optional<User> user = userService.findByEmail(principal.getName());
        User user1 = user.get();
        message.setUserId(user1);
        log.info("User who send message = {}", user1);

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        message.setLocalTime(LocalTime.parse(localTime.format(myFormatObj)));
        log.info("Message sending time = {}", LocalTime.parse(localTime.format(myFormatObj)));

        LocalDate localDate = LocalDate.now();
        message.setLocalDate(LocalDate.from(localDate));

        messageService.saveMessage(message);
        log.info("Message save` data = {}", message);

        Optional<User> userOptional = userService.findByEmail(message.getForTo());

        User user2 = userOptional.get();


        modelMap.addAttribute("messageUser", message);

        return "messages/userMessage";
    }

}
