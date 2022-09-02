package com.elpatron.cba;

import com.elpatron.cba.model.Role;
import com.elpatron.cba.model.User;
import com.elpatron.cba.service.RoleService;
import com.elpatron.cba.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class CbaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CbaApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            userService.addNewUser(new User(null, "test", "test", "test", new ArrayList<>()));
            userService.addNewUser(new User(null, "test2", "test2", "test2", new ArrayList<>()));
            userService.addNewUser(new User(null, "test3", "test3", "test3", new ArrayList<>()));

            roleService.addNewRole(new Role(null, "USER"));
            roleService.addNewRole(new Role(null, "ADMIN"));

            roleService.addUserRole("test" , "ADMIN");
            roleService.addUserRole("test" , "USER");
            roleService.addUserRole("test2" , "USER");
        };
    }*/
}
