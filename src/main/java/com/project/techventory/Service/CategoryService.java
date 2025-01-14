package com.project.techventory.Service;

import com.project.techventory.model.Category;
import com.project.techventory.model.Product;
import com.project.techventory.repository.CategoryRepository;
import com.project.techventory.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null); 
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category); 
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);  
    }

    public long getCategoryCount() {
        return categoryRepository.count();
    }
     public List<Product> getAllProducts() {
        return productRepository.findAllOrderByIdDesc(); 
    }
}