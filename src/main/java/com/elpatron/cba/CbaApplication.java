package com.elpatron.cba;

import com.elpatron.cba.domain.Role;
import com.elpatron.cba.domain.User;
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

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveUser(new User(null, "test", "test", "test", new ArrayList<>()));
            userService.saveUser(new User(null, "test2", "test2", "test2", new ArrayList<>()));
            userService.saveUser(new User(null, "test3", "test3", "test3", new ArrayList<>()));

            userService.saveRole(new Role(null, "USER"));
            userService.saveRole(new Role(null, "ADMIN"));

            userService.addRoleToUser("test" , "ADMIN");
            userService.addRoleToUser("test" , "USER");
            userService.addRoleToUser("test2" , "USER");
        };
    }
}
