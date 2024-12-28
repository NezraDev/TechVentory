package com.project.techventory.Service;

import java.util.Optional;

import com.project.techventory.model.User;


import com.project.techventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            // Use the Spring Security User object which implements UserDetails
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())// You may want to customize the authorities here
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
