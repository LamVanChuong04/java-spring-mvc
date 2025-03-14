package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hoidanit.laptopshop.controller.service.ProductService;
import vn.hoidanit.laptopshop.model.Product;
import vn.hoidanit.laptopshop.model.User;
import vn.hoidanit.laptopshop.model.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.UserService;


import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class HomePageController {
    private final ProductService productService;
    private final PasswordEncoder passwordEncoder; // Needed for password encryption
    private final UserService userService;
    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/")
    public String getProduct(Model model) {
        List<Product> products = this.productService.fetchProducts();
        model.addAttribute("products", products);
        return "client/homepage/homepage";
    }
    @GetMapping("/register")
     public String getRegisterPage(Model model) {
        model.addAttribute("register", new RegisterDTO());
         return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleSaveUser(@ModelAttribute("register") RegisterDTO registerDTO) {
       User user = this.userService.dtoToUser(registerDTO);
        String password = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole(this.userService.getRoleByName("USER"));
        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }
    
    @GetMapping("/login")
     public String getLoginPage(Model model) {
 
         return "client/auth/login";
     }
 
}
