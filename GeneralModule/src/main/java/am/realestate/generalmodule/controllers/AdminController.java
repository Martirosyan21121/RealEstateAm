package am.realestate.generalmodule.controllers;

import am.realestate.module.User;
import am.realestate.reposerviceconfig.repo.UserRepo;
import am.realestate.reposerviceconfig.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepo userRepo;

    @GetMapping("/admin111")
    public String admin(Model modelMap) {
        log.info("Admin page");
        List<User> user = userService.findAllUsers();
        modelMap.addAttribute("usersAdmin", user);
        log.info("All user size = {}", user.size());
        modelMap.addAttribute("usersSize", user.size());
        return "singlePage/adminPage";
    }

    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        log.info("deleted USER id = " + id);
        return "redirect:/admin111";
    }
}
