package com.project.techventory.Service;

import com.project.techventory.model.Product;
import com.project.techventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAllRandomOrder();
    }
    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }
    public long getProductCount(){
        return productRepository.count();
    }
}
