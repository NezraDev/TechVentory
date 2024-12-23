package com.project.techventory.Controller;

import com.project.techventory.model.User;
import com.project.techventory.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private Repository myRepository;

    @PostMapping(value = "/authentication/register", consumes ="application/json")
    public User createUser(@RequestBody User user){
        return myRepository.save(user);
    }
    

}
