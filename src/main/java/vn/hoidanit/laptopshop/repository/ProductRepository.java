package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.laptopshop.model.Product;

public interface  ProductRepository extends JpaRepository<Product, Long>{
    
}
