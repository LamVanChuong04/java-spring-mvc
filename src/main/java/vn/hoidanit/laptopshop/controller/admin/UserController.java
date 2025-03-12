package vn.hoidanit.laptopshop.controller.admin;

import vn.hoidanit.laptopshop.controller.service.UploadFileService;
import vn.hoidanit.laptopshop.model.User;

import vn.hoidanit.laptopshop.service.UserService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;





// @RestController
// public class UserController {
//     private UserService userService;
    
//     public UserController(UserService userService){
//         this.userService = userService;
//     }
//     @GetMapping("/")
//     public String  getHomePage(){
//         return this.userService.getHomePage();
//     }
// }

@Controller
public class UserController {
    private final UserService userService;
    private final UploadFileService uploadService;
    private final PasswordEncoder passwordEncoder;


    public UserController(ServletContext servletContext, UserService userService, UploadFileService uploadService, PasswordEncoder passwordEncoder) {
        this.uploadService = uploadService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @RequestMapping("/")
    public String  getHomePage(Model model){
        String test = this.userService.getHomePage();
        model.addAttribute("test", test);
        return "hello";
    }
     // create user
    @GetMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }
    @PostMapping(value = "/admin/user/create")
     public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User hoidanit,
            BindingResult newUserBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
         }

         if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(hoidanit.getPassword());

        hoidanit.setAvatar(avatar);
        hoidanit.setPassword(hashPassword);
        hoidanit.setRole(this.userService.getRoleByName(hoidanit.getRole().getName()));
        this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
     }
    // lấy danh sách user
    @GetMapping("/admin/user")
    public String  getUserPage(Model model){
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("user1", users);
        return "admin/user/show";
    }
    // lấy thông tin chi tiết user
    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);// hứng user được trả về từ service
        model.addAttribute("user", user);// truyền dữ liệu từ controller sang view
        model.addAttribute("id", id);

        return "admin/user/detail";
    }

    // update user
    @GetMapping("/admin/user/update/{id}") // GET
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") @Valid User chuong,
    BindingResult newUserBindingResult) {
        User currentUser = this.userService.getUserById(chuong.getId());
        if (currentUser != null) {
            currentUser.setAddress(chuong.getAddress());
            currentUser.setFullName(chuong.getFullName());
            currentUser.setPhone(chuong.getPhone());
            System.out.println("User updated: " + currentUser);
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }
    // delete
    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUser(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }
    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User hoidanit) {
        this.userService.deleteAUser(hoidanit.getId());
        return "redirect:/admin/user";
    }

}
