package com.elpatron.cba.controller;

import com.elpatron.cba.dto.UserRoleDTO;
import com.elpatron.cba.model.Role;
import com.elpatron.cba.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cba/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

    @PostMapping("/add")
    public ResponseEntity<Role> addNewRole(
            @Valid
            @RequestBody Role role
    ) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("/cba/roles/add")));
        return ResponseEntity.created(uri).body(roleService.addNewRole(role));
    }

    /*@PutMapping("/update/{roleID}")
    public void updateRole(
            @Valid
            @PathVariable("roleID") Long roleID,
            @RequestBody Role role
    ) {
        roleService.updateRole(role, roleID);
    }*/

    @DeleteMapping("/delete/{roleID}")
    public void deleteRole(
            @PathVariable("roleID") Long roleID
    ) {
        roleService.deleteRole(roleID);
    }

    @PostMapping("/addUserRole")
    public ResponseEntity<Void> addUserRole(
            @Valid
            @RequestBody UserRoleDTO userRoleDTO
    ) {
        roleService.addUserRole(userRoleDTO.getUsername(), userRoleDTO.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/removeUserRole")
    public ResponseEntity<Void> removedUserRole(
            @Valid
            @RequestBody UserRoleDTO userRoleDTO
    ) {
        roleService.removeUserRole(userRoleDTO.getUsername(), userRoleDTO.getRoleName());
        return ResponseEntity.ok().build();
    }
}
