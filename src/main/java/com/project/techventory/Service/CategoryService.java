package com.project.techventory.Service;

import com.project.techventory.model.Category;
import com.project.techventory.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllManufacturers() {
        return categoryRepository.findAll();
    }

    public Category getManufacturerById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveManufacturer(Category manufacturer) {
        return categoryRepository.save(manufacturer);
    }

    public void deleteManufacturer(int id) {
       categoryRepository.deleteById(id); 
    }
    public long getCategoryCount(){
        return categoryRepository.count();
    }
}