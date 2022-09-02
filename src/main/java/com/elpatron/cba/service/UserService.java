package com.elpatron.cba.service;

import com.elpatron.cba.exception.BadRequestException;
import com.elpatron.cba.exception.NotFoundException;
import com.elpatron.cba.model.Role;
import com.elpatron.cba.model.User;
import com.elpatron.cba.repository.RoleRepository;
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
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("user not found in db");
            throw new UsernameNotFoundException("user not found in db");
        } else {
            log.info("user {} found in db", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName()))
        );
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
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
            log.error("username is taken");
            throw new BadRequestException("username is taken");
        } else {
            log.info("saving new user {} to db", user.getName());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

    @Transactional
    public void updateUser(User user, Long userID) {
        User existingUser = userRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("user not found")
                );
        if (userRepository.existsByUsername(user.getUsername())) {
            if (Objects.equals(existingUser.getUsername(), user.getUsername())) {
                existingUser.setName(user.getName());
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(existingUser);
            } else {
                log.error("username is taken");
                throw new BadRequestException("username is taken");
            }
        } else {
            existingUser.setName(user.getName());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(existingUser);
        }
    }

    public void deleteUser(Long userID) {
        if (userRepository.existsById(userID)) {
            userRepository.deleteById(userID);
        } else {
            log.error("user with id {} not found", userID);
        }
    }

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        log.info("fetching all roles");
        return roleRepository.findAll();
    }

    public Role addNewRole(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            log.error("role already exists");
            throw new BadRequestException("role already exists");
        } else {
            log.info("saving new role {} to db", role.getName());
            role.setName(role.getName().toUpperCase());
            return roleRepository.save(role);
        }
    }

    /*@Transactional
    public void updateRole(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            log.error("role not found in db");
        } else {
            log.info("role {} found in db", roleName);
        }
        if (!Objects.equals(existingUser.getUsername(), user.getUsername())) {
            if (userRepository.existsByUsername(user.getUsername())) {
                log.error("username is taken");
            } else {
                existingUser.setName(user.getName());
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(existingUser);
            }
        }
    }*/

    public void deleteRole(Long roleID) {
        if (roleRepository.existsById(roleID)) {
            roleRepository.deleteById(roleID);
        } else {
            log.error("role with id {} not found", roleID);
        }
    }

    public void addUserRole(String username, String roleName) {
       /* User user = userRepository.findByUsername(username);
        for (Role roleDuplicate : user.getRoles()){
            if (roleDuplicate.getName().equals(roleName)){
                log.error("user already has this role");
                throw new BadRequestException("user already has this role");
            } else {
                Role role = roleRepository.findByName(roleName);
                log.info("adding role {} to user {} to db", roleName, username);
                user.getRoles().add(role);
            }
        }*/
        log.info("removing role {} from user {} from db", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public void removeUserRole(String username, String roleName) {
        log.info("removing role {} from user {} from db", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().remove(role);
    }
}
