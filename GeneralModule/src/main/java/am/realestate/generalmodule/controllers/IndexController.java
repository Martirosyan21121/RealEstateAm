package am.realestate.generalmodule.controllers;

import am.realestate.module.Home;
import am.realestate.reposerviceconfig.repo.HomeRepo;
import am.realestate.reposerviceconfig.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Slf4j
public class IndexController {


    @Value("${file.upload.dir}")
    private String uploadDir;
    @Autowired
    public UserService userService;

    @Autowired
    public HomeRepo homeRepo;

    @GetMapping("/")
    private String homePage(ModelMap modelMap) {
        List<Home> homes = homeRepo.findAll();
        modelMap.addAttribute("homes", homes);
        log.info("home page");
        return "firstPages/index";
    }

    @GetMapping("/about")
    private String about() {
        return "firstPages/about";
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

    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImage(@RequestParam("name") String imageName) throws IOException {
        log.info("image name = {}", imageName);
        InputStream in = Files.newInputStream(Paths.get(uploadDir + File.separator + imageName));
        return IOUtils.toByteArray(in);
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



    @GetMapping("/register")
    private String register() {
        return "register";
    }

    @GetMapping("/login")
    private String login() {
        return "login";
    }
}
