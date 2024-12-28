package com.project.techventory.Controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.project.techventory.model.Product;
import com.project.techventory.model.ProductEdit;
import com.project.techventory.repository.CategoryRepository;
import com.project.techventory.repository.ManufacturerRepository;
import com.project.techventory.repository.ProductRepository;

import jakarta.validation.Valid;


@Controller
public class AppController {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private ManufacturerRepository manufacturerRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    // Landing page
    @GetMapping("/")
    public String home() {
        return "landing/index";
    }

    // Login page
    @GetMapping("authentication/login")
    public String login() {
        return "authentication/login";
    }

    // Register page
    @GetMapping("authentication/register")
    public String register() {
        return "authentication/register";
    }

    // User's view page
    @GetMapping("user/view")
    public String view() {
        return "user/view";
    }

    // Admin login page
    @GetMapping("authentication/admin")
    public String admin() {
        return "authentication/admin";
    }

    // Admin dashboard
    @GetMapping("admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @RequestMapping("products/product")
    @GetMapping("products/product")
    public String product(Model model) {
        List<Product> products = repo.findAll();
        model.addAttribute("products", products);
        return "products/product";
    }

    @GetMapping("products/create")
    public String showCreatePage(Model model) {
        ProductEdit productEdit = new ProductEdit();
        model.addAttribute("productEdit", productEdit);
        model.addAttribute("manufacturers", manufacturerRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        return "products/create";
    }

    @PostMapping(value = "/products/create", consumes = "multipart/form-data")
    public String createPage(@Valid @ModelAttribute ProductEdit productEdit, BindingResult result) {
        if (productEdit.getImageFile().isEmpty()) {
            result.addError(new FieldError("productEdit", "productImage", "The image file is required"));
        }
        if (result.hasErrors()) {
            return "products/create";
        }

        MultipartFile image = productEdit.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDirectory = "public/images/";
            Path uploadPath = Paths.get(uploadDirectory);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, uploadPath.resolve(storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Product product = new Product();
        product.setProductName(productEdit.getName());
        product.setPrice(productEdit.getPrice());
        product.setManufacturer(productEdit.getManufacturer());
        product.setCategory(productEdit.getCategory());
        product.setCreatedAt(createdAt);
        product.setProductImage(storageFileName);

        repo.save(product);
        return "redirect:/products/product";
    }
    
}
