package vn.hoidanit.laptopshop.controller;

import vn.hoidanit.laptopshop.model.User;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;


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
    private UserService userService;
    
    public UserController(UserService userService){
        this.userService = userService;
    }
    @RequestMapping("/")
    public String  getHomePage(Model model){
        String test = this.userService.getHomePage();
        model.addAttribute("test", test);
        return "hello";
    }
    @RequestMapping("/admin/user")
    public String  getUserPage(Model model){
        model.addAttribute("newUser", new User());   
        return "admin/user/create";
    }
    @RequestMapping(value = "/admin/user/create1", method = RequestMethod.POST)
     public String createUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
        System.out.println(" run here " + hoidanit);
        
        return "hello";
    }
}
