package com.elpatron.cba.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.elpatron.cba.model.Role;
import com.elpatron.cba.model.User;
import com.elpatron.cba.service.UserService;
import com.elpatron.cba.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cba/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/user/{username}")
    public User getUserByUsername(
           @PathVariable("username") String username
    ) {
        return userService.getUser(username);
    }

    @PostMapping
    public ResponseEntity<User> addNewUser(
            @Valid
            @RequestBody User user
    ) {
        Utility utility = new Utility();
        return ResponseEntity.created(utility.setURI("/cba/users")).body(userService.addNewUser(user));
    }

    @PutMapping("/{userID}")
    public void updateUser(
            @Valid
            @PathVariable("userID") Long userID,
            @RequestBody User user
    ) {
        userService.updateUser(userID, user);
    }

    @DeleteMapping("/{userID}")
    public void deleteUser(
            @PathVariable("userID") Long userID
    ) {
        userService.deleteUser(userID);
    }

    @GetMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            Utility utility = new Utility();
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                utility.setTokens(access_token ,refresh_token, response);
            } catch (Exception exception) {
                utility.getException(response, exception);
            }
        } else {
            throw new RuntimeException("refresh token is missing");
        }
    }
}
