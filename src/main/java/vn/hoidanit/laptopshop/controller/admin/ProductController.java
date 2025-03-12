package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.controller.service.ProductService;
import vn.hoidanit.laptopshop.controller.service.UploadFileService;
import vn.hoidanit.laptopshop.model.Product;

;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadFileService uploadFileService;
 
    public ProductController(ProductService productService, UploadFileService uploadFileService) {
        this.productService = productService;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Product> products = this.productService.fetchProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create") // GET
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
     public String handleCreateProduct(
             @ModelAttribute("newProduct") @Valid Product pr,
             BindingResult newProductBindingResult,
             @RequestParam("hoidanitFile") MultipartFile file) {
         // validate
         if (newProductBindingResult.hasErrors()) {
             return "admin/product/create";
         }
 
         // upload image
         String image = this.uploadFileService.handleSaveUploadFile(file, "product");
         pr.setImage(image);
 
         this.productService.createProduct(pr);
 
         return "redirect:/admin/product";
     }
    // trang chi tiet san pham
    @GetMapping("/admin/product/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        Product pr = this.productService.findProductById(id);// hứng user được trả về từ service
        model.addAttribute("product", pr);// truyền dữ liệu từ controller sang view
        model.addAttribute("id", id);

        return "admin/product/detail";
    }

 }
