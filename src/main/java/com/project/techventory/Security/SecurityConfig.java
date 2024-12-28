package com.project.techventory.Security;


import com.project.techventory.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final UserService appUserService;

    @Bean
    public UserDetailsService userDetailsService(){
        return appUserService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpForm -> {
                    httpForm
                            .loginPage("/authentication/login").permitAll();
                    httpForm
                            .defaultSuccessUrl("/user/view");
                })

                .authorizeHttpRequests(register->{
                    register.requestMatchers("/products/create", "/products/product" , "/admin/dashboard", "/user/view","/authentication/admin", "/authentication/register", "/", "/css/**", "/js/**" ,"/images/**").permitAll() ;
                    register.anyRequest().authenticated();
                })
                .logout(logout->{
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/authentication/login");
                    logout.invalidateHttpSession(true);
                    logout.clearAuthentication(true);
                    logout.permitAll();
                })
                .build();
    }
}