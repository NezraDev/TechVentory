package com.project.techventory.repository;

import com.project.techventory.model.Category; 

import com.project.techventory.model.Manufacturer;
import com.project.techventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> { 
   
    @Query(value = "SELECT * FROM product ORDER BY id DESC", nativeQuery = true)
    List<Product> findAllOrderByIdDesc();
    
    // Find products by category and manufacturer
    List<Product> findByCategoryAndManufacturer(Category category, Manufacturer manufacturer);
   
    // Find products by category only
    List<Product> findByCategory(Category category);
   
    // Find products by manufacturer only
    List<Product> findByManufacturer(Manufacturer manufacturer);
   
    @Query("SELECT p FROM Product p WHERE " +
    "(:productName IS NULL OR p.productName LIKE %:productName%) " +
    "ORDER BY p.id DESC")
    List<Product> searchProducts(@Param("productName") String productName);

}