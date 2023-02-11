package am.realestate.generalmodule;

import am.realestate.module.User;
import am.realestate.module.UserType;
import am.realestate.reposerviceconfig.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@EnableJpaRepositories(basePackages = {"am.realestate.reposerviceconfig.repo"})
@EntityScan(value = {"am.realestate.module"})
@ComponentScan(value = {"am.realestate.generalmodule", "am.realestate.module", "am.realestate.reposerviceconfig"})
@SpringBootApplication
public class GeneralModuleApplication implements CommandLineRunner {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(GeneralModuleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.findByEmail("narek@nm").isEmpty()) {
            userRepo.save(User.builder()
                    .name("Narek")
                    .surname("Martirosyan")
                    .email("narek@nm")
                    .phoneNumber(77113162)
                    .localDate(LocalDate.parse("2023-01-20"))
                    .isActive(true)
                    .userType(UserType.ADMIN)
                    .password(passwordEncoder.encode("077113162"))
                    .build());
        }
    }
}
