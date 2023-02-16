package am.realestate.reposerviceconfig.service;

import am.realestate.module.Home;
import am.realestate.module.User;
import am.realestate.reposerviceconfig.repo.HomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HomeService {
    @Autowired
    private HomeRepo homeRepo;

    public void saveHome(Home home){
        homeRepo.save(home);
    }

    public Optional<Home> findHomeById(long id){
        return homeRepo.findById(id);
    }
}
