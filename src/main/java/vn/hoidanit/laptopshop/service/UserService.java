package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.model.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public String getHomePage() {
        return "Hoi Dan IT";
    }
    public User handleSaveUser(User user) {
        User test = this.userRepository.save(user);
        return test;
    }
    public User getUserById(long id) {
        return this.userRepository.findById(id);// trả về user ứng với id
    }
    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }
}
