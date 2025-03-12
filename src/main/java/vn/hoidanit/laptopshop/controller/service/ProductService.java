package vn.hoidanit.laptopshop.controller.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.model.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    // tạo product
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }
    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }
    
    public  void deleteProduct(long id){
        this.productRepository.deleteById(id);
    }
    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    

}
