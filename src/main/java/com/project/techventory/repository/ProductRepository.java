package com.project.techventory.repository;

import com.project.techventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

   @Query(value = "SELECT * FROM product ORDER BY RAND()", nativeQuery = true)
    List<Product> findAllRandomOrder();
}
