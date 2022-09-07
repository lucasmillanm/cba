package com.elpatron.cba.service;

import com.elpatron.cba.exception.BadRequestException;
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
import java.util.Locale;
import java.util.Objects;
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
            log.error("role already exists");
            throw new BadRequestException("role already exists");
        } else {
            log.info("saving new role {} to db", role.getName());
            role.setName(role.getName().toUpperCase());
            return roleRepository.save(role);
        }
    }

    @Transactional
    public void updateRole(Long roleID, Role role) {
        Role existingRole = roleRepository.findById(roleID)
                .orElseThrow(() -> new NotFoundException("role not found")
                );
        if (roleRepository.existsByName(role.getName())) {
            if (Objects.equals(existingRole.getName(), role.getName())) {
                throw new BadRequestException("role name should not be the same as before");
            } else {
                log.error("role name is taken");
                throw new BadRequestException("role name is taken");
            }
        } else {
            existingRole.setName(role.getName().toUpperCase());
            roleRepository.save(existingRole);
        }
    }

    public void deleteRole(Long roleID) {
        if (roleRepository.existsById(roleID)) {
            roleRepository.deleteById(roleID);
        } else {
            log.error("role with id {} not found", roleID);
            throw new NotFoundException("role not found");
        }
    }

    public void addUserRole(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("user not found");
        } else {
            /*if (this.getUserRoles(user).contains(roleName)) {
                throw new BadRequestException("this user already has this role");
            } else {

            }*/
            for (String role : getUserRoles(user)) {
                if (role.equals(roleName)) {
                    throw new BadRequestException("user already has this role");
                } else {
                    log.info("user does not contain this role");
                }
            }
            Role role = roleRepository.findByName(roleName);
            log.info("adding role {} to user {} to db", roleName, username);
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    public void removeUserRole(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        if (user == null || role == null) {
            throw new NotFoundException("user or role not found");
        } else if (user.getRoles().isEmpty()) {
            throw new NotFoundException("this user does not have any roles");
        } else if (!this.getUserRoles(user).contains(roleName.toUpperCase())) {
            throw new NotFoundException("this user does not have this role");
        } else {
            log.info("removing role {} from user {} from db", roleName, username);
            user.getRoles().remove(role);
            userRepository.save(user);
        }
    }

    public List<String> getUserRoles(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
