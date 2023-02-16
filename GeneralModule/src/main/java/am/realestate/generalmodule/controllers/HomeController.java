package am.realestate.generalmodule.controllers;

import am.realestate.module.Home;
import am.realestate.module.User;
import am.realestate.reposerviceconfig.repo.HomeRepo;
import am.realestate.reposerviceconfig.service.HomeService;
import am.realestate.reposerviceconfig.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    public UserService userService;
    @Autowired
    public HomeService homeService;
    @Autowired
    public HomeRepo homeRepo;


    @GetMapping("/addHome")
    private String addHome() {
        return "home/addHome";
    }

    @PostMapping("/addHome")
    private String addHomePost(@ModelAttribute Home home, Random random, Principal principal, ModelMap modelMap) {
        String userEmail = principal.getName();
        log.info("User email for save home = {}", userEmail);
        Optional<User> optionalUser = userService.findByEmail(userEmail);
        home.setUserId(optionalUser.get());
        int homeCode = random.nextInt(1000, 999999);
        home.setHomeCode(homeCode);
        homeService.saveHome(home);
        return "redirect:/addHome";
    }

    @GetMapping("/property-detail")
    private String detailPage(ModelMap modelMap, @RequestParam("id") long id) {
        log.info("Home id = {}", id);
        Optional<Home> home = homeService.findHomeById(id);
        Home home1 = home.get();
        modelMap.addAttribute("homeData", home1);
        return "property-detail";
    }
}
