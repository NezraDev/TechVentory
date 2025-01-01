package com.project.techventory.repository;



import com.project.techventory.model.Manufacturer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    Manufacturer findByName(String name);
}
