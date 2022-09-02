package com.elpatron.cba.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.elpatron.cba.dto.UserRoleDTO;
import com.elpatron.cba.model.Role;
import com.elpatron.cba.model.User;
import com.elpatron.cba.service.UserService;
import com.elpatron.cba.utility.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cba")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/users/add")
    public ResponseEntity<User> addNewUser(
            @Valid
            @RequestBody User user
    ) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("/cba/users/add")));
        return ResponseEntity.created(uri).body(userService.addNewUser(user));
    }

    @PutMapping("/users/update/{userID}")
    public void updateUser(
            @Valid
            @PathVariable("userID") Long userID,
            @RequestBody User user
    ) {
        userService.updateUser(user, userID);
    }

    @DeleteMapping("/users/delete/{userID}")
    public void deleteUser(
            @PathVariable("userID") Long userID
    ){
        userService.deleteUser(userID);
    }

    @GetMapping("/roles/all")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(userService.getAllRoles());
    }

    @PostMapping("/roles/add")
    public ResponseEntity<Role> addNewRole(
            @Valid
            @RequestBody Role role
    ) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("/cba/roles/add")));
        return ResponseEntity.created(uri).body(userService.addNewRole(role));
    }

    /*@PutMapping("/update/{roleID}")
    public void updateRole(
            @Valid
            @PathVariable("roleID") Long roleID,
            @RequestBody Role role
    ) {
        roleService.updateRole(role, roleID);
    }*/

    @DeleteMapping("/roles/delete/{roleID}")
    public void deleteRole(
            @PathVariable("roleID") Long roleID
    ) {
        userService.deleteRole(roleID);
    }

    @PostMapping("/roles/addUserRole")
    public ResponseEntity<Void> addUserRole(
            @Valid
            @RequestBody UserRoleDTO userRoleDTO
    ) {
        userService.addUserRole(userRoleDTO.getUsername(), userRoleDTO.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/roles/removeUserRole")
    public ResponseEntity<Void> removeUserRole(
            @Valid
            @RequestBody UserRoleDTO userRoleDTO
    ) {
        userService.removeUserRole(userRoleDTO.getUsername(), userRoleDTO.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                Utility utility = new Utility();
                utility.getException(response, exception);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
