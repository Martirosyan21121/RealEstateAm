package am.realestate.reposerviceconfig.service;

import am.realestate.module.User;
import am.realestate.reposerviceconfig.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    public Optional<User> findByToken(Integer token){
        return userRepo.findByToken(token);
    }
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public List<User> findAllUsers(){
        return userRepo.findAll();
    }

    public Optional<User> findUserById(long id){
        return userRepo.findById(id);
    }
}
