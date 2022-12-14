package com.elpatron.cba.service;

import com.elpatron.cba.exception.MethodNotAllowedException;
import com.elpatron.cba.exception.NotFoundException;
import com.elpatron.cba.model.User;
import com.elpatron.cba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            log.warn("user {} not found in db", username);
            throw new UsernameNotFoundException("user not found in db");
        } else {
            log.info("user {} found in db", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        existingUser.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName()))
        );
        return new org.springframework.security.core.userdetails.User(existingUser.getUsername(), existingUser.getPassword(), authorities);
    }

    public List<User> getAllUsers() {
        log.info("fetching all users");
        return userRepository.findAll();
    }

    public User getUser(String username) {
        log.info("fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    public User addNewUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            log.warn("username is taken");
            throw new MethodNotAllowedException("username is taken");
        } else {
            log.info("adding new user {}", user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

    @Transactional
    public void updateUser(Long userID, User user) {
        User existingUser = userRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("user not found")
                );
        if (userRepository.existsByUsername(user.getUsername())) {
            if (Objects.equals(existingUser.getUsername(), user.getUsername())) {
                log.info("updating user {}", user.getUsername());
                this.saveUser(existingUser, user);
            } else {
                log.warn("username is taken");
                throw new MethodNotAllowedException("username is taken");
            }
        } else {
            log.info("updating user {}", user.getUsername());
            this.saveUser(existingUser, user);
        }
    }

    public void deleteUser(Long userID) {
        if (userRepository.existsById(userID)) {
            log.info("deleting user with id {}", userID);
            userRepository.deleteById(userID);
        } else {
            log.warn("user with id {} not found", userID);
            throw new NotFoundException("user not found");
        }
    }

    public void saveUser(User existingUser, User user) {
        existingUser.setName(user.getName());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(existingUser);
    }
}
