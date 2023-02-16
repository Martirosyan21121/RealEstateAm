package am.realestate.generalmodule.controllers;

import am.realestate.module.User;
import am.realestate.module.UserType;
import am.realestate.reposerviceconfig.service.EmailService;
import am.realestate.reposerviceconfig.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
@Slf4j
public class UserController {

    @Value("${file.upload.dir}")
    private String uploadDir;
    @Autowired
    public UserService userService;

    @Autowired
    public EmailService emailService;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @PostMapping("/fail_login")
    private String failLogin(ModelMap modelMap) {
        log.warn("Fail login");
        String msg = "Wrong email or password";
        modelMap.addAttribute("error", msg);
        return "login";
    }

    @PostMapping("/save")
    private String save(@ModelAttribute User user, ModelMap modelMap, Locale locale, @RequestParam("image") MultipartFile file) throws MessagingException, IOException {
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            log.info("email already exist = {}", user.getEmail());
            String msg = "Your email` " + user.getEmail() + " already exist";
            modelMap.addAttribute("exist", msg);
            return "register";

        } else {
            String profilePic = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File image = new File(uploadDir, profilePic);
            file.transferTo(image);
            user.setProfilePic(profilePic);
            user.setActive(false);
            user.setUserType(UserType.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            LocalDate localDate = LocalDate.now();
            user.setLocalDate(LocalDate.from(localDate));
            userService.saveUser(user);
            log.info("user saved = {}", user.getEmail());
//
//            String link = "http://localhost:8080/myAccount";
//            emailService.sendHtmlEmail(user.getEmail(), "Welcome", user, link, "email/registerEmail.html", locale);
            return "redirect:/";
        }
    }



    @PostMapping("/activate")
    private String activate(@ModelAttribute User user, Random random, ModelMap modelMap) {
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
        if (byEmail.isEmpty()) {
            log.info("This email dose not exist = {}", user.getEmail());
            String message = "This email dose not exist";
            modelMap.addAttribute("msg", message);
            return "verification/emailActivate";
        }
        User user1 = byEmail.get();
        int activateCode = random.nextInt(100000, 999999);
        user1.setToken(activateCode);
        userService.saveUser(user1);
        log.info("Token saved = {}", user1.getToken());
        emailService.send(user1.getEmail(), "Email verification", "Dear " + user1.getName() + " " + user1.getSurname() + "  \n This is Your verification code \n " + activateCode);
        return "verification/activateCode";
    }

    @PostMapping("/activateCode")
    private String activate(@RequestParam("token") Integer token, ModelMap modelMap) {
        Optional<User> optionalUser = userService.findByToken(token);
        if (optionalUser.isEmpty()) {
            log.info("Activate code dose not right = {}", optionalUser);
            String message = "Activate code dose not right";
            modelMap.addAttribute("msg", message);
            return "verification/activateCode";
        } else {
            User user1 = optionalUser.get();
            user1.setToken(0);
            user1.setActive(true);
            userService.saveUser(user1);
            log.info("User saved Activate True");
            return "redirect:/?msg=User is Activated";
        }
    }

    @GetMapping("/forgotPassword")
    private String forgotPassword() {
        return "verification/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    private String forgotPasswordPost(@ModelAttribute User user, ModelMap modelMap, Random random) {
        Optional<User> optionalUser = userService.findByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            log.info("This email does not exist = {}", user.getEmail());
            String msg = "This email` " + user.getEmail() + " `dose not exist";
            modelMap.addAttribute("msg", msg);
            return "verification/forgotPassword";

        } else {
            User user1 = optionalUser.get();
            int verificationCode = random.nextInt(100000, 999999);
            user1.setToken(verificationCode);
            userService.saveUser(user1);
            log.info("User saves, verification code is = {}", verificationCode);
            emailService.send(user1.getEmail(), "Email verification", "Dear " + user1.getName() + " " + user1.getSurname() + "  \n This is Your verification code \n " + verificationCode);
            return "verification/verifyCode";
        }
    }

    @PostMapping("/resetPassword")
    private String resetPassword(@RequestParam("token") Integer token, ModelMap modelMap) {
        Optional<User> optionalUser = userService.findByToken(token);
        if (optionalUser.isEmpty()) {
            log.info("Your verify code does not right = {}", optionalUser);
            String msg = "Your verify code does not right";
            modelMap.addAttribute("message", msg);
            return "verification/verifyCode";
        } else {
            log.info("Your verify code is right = {}", optionalUser);
            modelMap.addAttribute("email", optionalUser.get().getEmail());
            return "verification/changePassword";
        }
    }

    @PostMapping("/changePassword")
    private String changePassword(@RequestParam("email") String email, @RequestParam("password") String password, Locale locale) throws MessagingException {
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            user.setToken(0);
            user.setPassword(passwordEncoder.encode(password));
            userService.saveUser(user);
            log.info("Your password change = {}", user.getEmail());
            String link = "http://localhost:8080/";
            emailService.sendHtmlEmail(user.getEmail(), "Welcome", user, link, "email/changePassword.html", locale);
        }
        return "redirect:/";
    }

    @GetMapping("/myAccount")
    private String myAccount(@ModelAttribute User user, Principal principal, ModelMap modelMap) {
        String email = principal.getName();
        Optional<User> userOptional = userService.findByEmail(email);
        User user1 = userOptional.get();
        if (user1.isActive()) {
            List<User> userList = new ArrayList<>();
            userOptional.ifPresent(userList::add);
            for (User u : userList) {
                log.info("USER account data - user.name = {}", u.getName());
                log.info(" user.surname = {}", u.getSurname());
                log.info(" user.email = {}", u.getEmail());
            }
            String activate = "Activate";
            modelMap.addAttribute("userList", userList);
            modelMap.addAttribute("activate", activate);
            return "singlePage/userSinglePage";
        } else {
            return "verification/emailActivate";
        }
    }

    @GetMapping("/editProfile")
    private String editProfile(Principal principal, ModelMap modelMap) {
        Optional<User> userOptional = userService.findByEmail(principal.getName());
        log.info("Started edit profile with email = {}", userOptional);
        List<User> userList = new ArrayList<>();
        userOptional.ifPresent(userList::add);
        modelMap.addAttribute("userData", userList);
        return "singlePage/editProfile";
    }

    @PostMapping("/updateProfile")
    private String editProfile(@ModelAttribute User user, @RequestParam("id") long id, Locale locale, @RequestParam("image") MultipartFile file) throws MessagingException, IOException {
        Optional<User> userOptional = userService.findUserById(id);
        log.info("Optional user = {}", userOptional);
        log.info("Optional user id = {}", id);
        String profilePic = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File image = new File(uploadDir, profilePic);
        file.transferTo(image);
        User user1 = userOptional.get();
        user.setActive(true);
        user.setEmail(user1.getEmail());
        user.setToken(user1.getToken());
        user.setUserType(user1.getUserType());
        user.setProfilePic(profilePic);
        user.setLocalDate(LocalDate.from(user1.getLocalDate()));
        user.setPassword(user1.getPassword());
        log.info("User Update data = {}", user);
        userService.saveUser(user);
        log.info("Account update = {}", user);
        log.info("user saved = {}", user.getEmail());
//
//        String link = "http://localhost:8080/myAccount";
//        emailService.sendHtmlEmail(user.getEmail(), "Welcome", user, link, "email/updateAccount.html", locale);
        return "redirect:/myAccount";

    }


}

