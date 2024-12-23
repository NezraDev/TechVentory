package com.project.techventory.repository;

import java.util.Optional;

import com.project.techventory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
