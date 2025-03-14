package vn.hoidanit.laptopshop.service;

import java.util.List;


import org.springframework.stereotype.Service;


import vn.hoidanit.laptopshop.model.Role;
import vn.hoidanit.laptopshop.model.User;
import vn.hoidanit.laptopshop.model.dto.RegisterDTO;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    // constructor
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    // lấy tất cả user
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
    // lấy user theo email
    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public String getHomePage() {
        return "Hoi Dan IT";
    }
    // hàm save user sau khi tạo/delete
    public User handleSaveUser(User user) {
        User users = this.userRepository.save(user);
        return users;
    }
    // lấy user theo id
    public User getUserById(long id) {
        return this.userRepository.findById(id);// trả về user ứng với id
    }
    // xóa user theo id
    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }
    // lấy role theo id
    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
    // DTO
    public User dtoToUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setAddress(registerDTO.getAddress());
        user.setPassword(registerDTO.getPassword());
        return user;
    }
    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
