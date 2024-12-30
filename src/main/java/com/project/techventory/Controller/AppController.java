package com.project.techventory.Controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.project.techventory.Service.CategoryService;
import com.project.techventory.Service.ManufacturerService;
import com.project.techventory.Service.ProductService;
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

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

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
        long manufacturerCount = manufacturerService.getManufacturerCount();
        long productCount = productService.getProductCount();
        long categoryCount = categoryService.getCategoryCount();
        List<Product> products = repo.findAllRandomOrder();
        model.addAttribute("products", products);
        model.addAttribute("manufacturerCount", manufacturerCount);
        model.addAttribute("productCount", productCount);
        model.addAttribute("categoryCount", categoryCount);
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

    @GetMapping("products/edit/{id}")
    public String showEditPage(Model model, @PathVariable int id) {
        Optional<Product> optionalProduct = repo.findById(id);
        if (optionalProduct.isEmpty()) {
            System.out.println("Product not found with ID: " + id);
            return "redirect:/products/product";  // Redirect to the list of products
        }
    
        Product product = optionalProduct.get();
    
        // Create a ProductEdit object and copy values from Product
        ProductEdit productEdit = new ProductEdit();
        productEdit.setId(product.getId());
        productEdit.setName(product.getProductName());
        productEdit.setPrice(product.getPrice());
        productEdit.setCategory(product.getCategory());
        productEdit.setManufacturer(product.getManufacturer());
       
    
        model.addAttribute("product", product);  // Add the Product object
        model.addAttribute("productEdit", productEdit);  // Add the populated ProductEdit object
    
        // Add categories and manufacturers
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("manufacturers", manufacturerRepo.findAll());
    
        return "products/edit";
    }
    

    @PostMapping("products/edit/{id}")
public String updateProduct(
        @Valid @ModelAttribute ProductEdit productEdit,
        BindingResult bindingResult,
        @PathVariable int id,  // Use @PathVariable instead of @RequestParam
        Model model) {
    if (bindingResult.hasErrors()) {
        return "products/edit";  // Return to edit page if there are errors
    }

    try {
        Product product = repo.findById(id).orElseThrow(() -> new Exception("Product not found"));
        model.addAttribute("products", product);

        // Handle image update
        if (!productEdit.getImageFile().isEmpty()) {
            String uploadDirectory = "public/images/";
            Path oldImagePath = Paths.get(uploadDirectory + product.getProductImage());

            // Delete the old image
            try {
                Files.deleteIfExists(oldImagePath);
            } catch (Exception ex) {
                System.out.println("Exception during old image deletion: " + ex.getMessage());
            }

            // Save the new image
            MultipartFile image = productEdit.getImageFile();
            Date createdAt = new Date();
            String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDirectory + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
            product.setProductImage(storageFileName);  // Set the new image file name
        }

        // Update the other product fields
        product.setProductName(productEdit.getName());
        product.setCategory(productEdit.getCategory());
        product.setManufacturer(productEdit.getManufacturer());
        product.setPrice(productEdit.getPrice());

        // Save the updated product
        repo.save(product);
    } catch (Exception ex) {
        System.out.println("Exception during product update: " + ex.getMessage());
    }

    return "redirect:/products/product";  // Redirect to the product list after update
}


    @GetMapping("products/delete")
    public String deleteProduct(@RequestParam int id) {
        try {
            Product product = repo.findById(id).orElseThrow(() -> new Exception("Product not found"));
            Path imagePath = Paths.get("public/images/" + product.getProductImage());
            try {
                Files.delete(imagePath);
            } catch (Exception ex) {
                System.out.println("Exception during image deletion: " + ex.getMessage());
            }
            repo.delete(product);
        } catch (Exception ex) {
            System.out.println("Exception during product deletion: " + ex.getMessage());
        }
        return "redirect:/products/product";
    }
}
