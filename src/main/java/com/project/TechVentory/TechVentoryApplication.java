package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.project.Repository") // Ensure this points to your repository package
@EntityScan("com.project.Model") // Ensure this points to your model package containing @Entity classes
public class TechVentoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(TechVentoryApplication.class, args);
    }
}