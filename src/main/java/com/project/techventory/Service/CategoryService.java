package com.project.techventory.Service;

import com.project.techventory.model.Category;
import com.project.techventory.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository manufacturerRepository;

    public List<Category> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Category getManufacturerById(int id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    public Category saveManufacturer(Category manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturer(int id) {
        manufacturerRepository.deleteById(id); 
    }
}