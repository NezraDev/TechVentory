package com.project.techventory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String home() {
        return "landing/index";
    }

    @GetMapping("authentication/login")
    public String login() {
        return "authentication/login";
    }

    @GetMapping("authentication/register")
    public String register() {
        return "authentication/register";
    }

    @GetMapping("authentication/admin")
    public String admin() {
        return "authentication/admin";
    }
    @GetMapping("user/dashboard")
    public String user(){
        return "user/dashboard";
    }


}