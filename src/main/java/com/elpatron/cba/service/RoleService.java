package com.elpatron.cba.service;

import com.elpatron.cba.exception.MethodNotAllowedException;
import com.elpatron.cba.exception.NotFoundException;
import com.elpatron.cba.model.Role;
import com.elpatron.cba.model.User;
import com.elpatron.cba.repository.RoleRepository;
import com.elpatron.cba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        log.info("fetching all roles");
        return roleRepository.findAll();
    }

    public Role addNewRole(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            log.warn("role already exists");
            throw new MethodNotAllowedException("role already exists");
        } else {
            role.setName(role.getName().toUpperCase());
            log.info("adding new role {}", role.getName());
            return roleRepository.save(role);
        }
    }

    @Transactional
    public void updateRole(Long roleID, Role role) {
        Role existingRole = roleRepository.findById(roleID)
                .orElseThrow(() -> new NotFoundException("role not found")
                );
        if (roleRepository.existsByName(role.getName())) {
            log.warn("role name is taken or same as before");
            throw new MethodNotAllowedException("role name is taken or same as before");
        } else {
            log.info("updating role {} to {}", existingRole.getName(), role.getName().toUpperCase());
            existingRole.setName(role.getName().toUpperCase());
            roleRepository.save(existingRole);
        }
    }

    public void deleteRole(Long roleID) {
        if (roleRepository.existsById(roleID)) {
            log.info("deleting role with id {}", roleID);
            roleRepository.deleteById(roleID);
        } else {
            log.warn("role with id {} not found", roleID);
            throw new NotFoundException("role not found");
        }
    }

    public void addUserRole(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("user not found");
        } else {
            if (this.containsUserRoles(user, roleName)) {
                throw new MethodNotAllowedException("this user already has this role");
            } else {
                Role role = roleRepository.findByName(roleName);
                if (role == null) {
                    throw new NotFoundException("role not found");
                }
                log.info("adding role {} to user {}", roleName, username);
                user.getRoles().add(role);
                userRepository.save(user);
            }
        }
    }

    public void removeUserRole(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        if (user == null || role == null) {
            log.warn("user or role not found");
            throw new NotFoundException("user or role not found");
        } else if (user.getRoles().isEmpty()) {
            log.warn("this user does not have any roles");
            throw new NotFoundException("this user does not have any roles");
        } else if (!this.containsUserRoles(user, roleName)) {
            log.warn("this user does not have this role");
            throw new NotFoundException("this user does not have this role");
        } else {
            log.info("removing role {} from user {}", roleName, username);
            user.getRoles().remove(role);
            userRepository.save(user);
        }
    }

    public boolean containsUserRoles(User user, String roleName) {
        List<String> userRoles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        return userRoles.contains(roleName.toUpperCase());
    }
}
