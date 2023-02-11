package am.realestate.generalmodule.controllers;

import am.realestate.module.User;
import am.realestate.reposerviceconfig.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Optional;

@Controller
@Slf4j
public class Index {

    @Autowired
    public UserService userService;

    @GetMapping("/")
    private String homePage() {
        log.info("home page");
        return "firstPages/index";
    }

    @GetMapping("/about")
    private String about(@ModelAttribute User user, Principal principal) {
        String email = principal.getName();
        Optional<User> userOptional = userService.findByEmail(email);
        User user1 = userOptional.get();
        if (user1.isActive()) {
            return "firstPages/about";
        } else {
            return "verification/emailActivate";
        }
    }

    @GetMapping("/agents")
    private String agents() {
        System.out.println("agents page");
        return "firstPages/agents";
    }

    @GetMapping("/blog")
    private String blog() {
        return "firstPages/blog";
    }

    @GetMapping("/contact")
    private String contact() {
        return "firstPages/contact";
    }

    @GetMapping("/buysalerent")
    private String buysalerent() {
        return "buysalerent";
    }

    @GetMapping("/blogdetail")
    private String blogdetail() {
        return "blogdetail";
    }

    @GetMapping("/property-detail")
    private String property() {
        return "property-detail";
    }

    @GetMapping("/register")
    private String register() {
        return "register";
    }

    @GetMapping("/login")
    private String login() {
        return "login";
    }
}
