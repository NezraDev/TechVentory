package com.project.techventory.Service;

import com.project.techventory.model.Manufacturer;
import com.project.techventory.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturerById(int id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturer(int id) {
        manufacturerRepository.deleteById(id);
    }

    public long getManufacturerCount() {
        return manufacturerRepository.count();
    }
    
}