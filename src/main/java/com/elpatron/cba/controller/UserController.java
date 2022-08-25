package com.elpatron.cba.controller;

import com.elpatron.cba.domain.Role;
import com.elpatron.cba.domain.User;
import com.elpatron.cba.dto.UserRoleDTO;
import com.elpatron.cba.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("users")
    public ResponseEntity<List<User>>getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("user/save")
    public ResponseEntity<User>saveUser(
            @RequestBody User user
    ) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save")));
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("role/save")
    public ResponseEntity<Role>saveRole(
            @RequestBody Role role
    ) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save")));
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("role/addToUser")
    public ResponseEntity<Void>addRoleToUser(
            @RequestBody UserRoleDTO userRoleDTO
    ) {
        userService.addRoleToUser(userRoleDTO.getUsername(), userRoleDTO.getRoleName());
        return ResponseEntity.ok().build();
    }
}
