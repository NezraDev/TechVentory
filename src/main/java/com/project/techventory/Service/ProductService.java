package com.project.techventory.Service;

import com.project.techventory.model.Category;
import com.project.techventory.model.Manufacturer;
import com.project.techventory.model.Product;
import com.project.techventory.repository.CategoryRepository;
import com.project.techventory.repository.ManufacturerRepository;
import com.project.techventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAllOrderByIdDesc(); // Use descending order by ID
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public long getProductCount() {
        return productRepository.count();
    }

    public List<Product> filterProducts(Integer categoryId, Integer manufacturerId) {
        // Handle the cases where categoryId and manufacturerId are null
        if (categoryId == null && manufacturerId == null) {
            return productRepository.findAllOrderByIdDesc(); // Return all products ordered by ID descending
        }

        // Fetch the Category and Manufacturer only if their IDs are provided
        Category category = (categoryId != null) ? categoryRepository.findById(categoryId).orElse(null) : null;
        Manufacturer manufacturer = (manufacturerId != null) ? manufacturerRepository.findById(manufacturerId).orElse(null) : null;

        // Filter products based on available category and manufacturer
        if (category != null && manufacturer != null) {
            return productRepository.findByCategoryAndManufacturer(category, manufacturer);
        } else if (category != null) {
            return productRepository.findByCategory(category);
        } else if (manufacturer != null) {
            return productRepository.findByManufacturer(manufacturer);
        } else {
            return productRepository.findAllOrderByIdDesc(); // Return all products ordered by ID descending
        }
    }
}