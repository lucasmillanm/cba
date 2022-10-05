package com.elpatron.cba.controller;

import com.elpatron.cba.dto.UserRoleDTO;
import com.elpatron.cba.model.Role;
import com.elpatron.cba.service.RoleService;
import com.elpatron.cba.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cba/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

    @PostMapping
    public ResponseEntity<Role> addNewRole(
            @Valid
            @RequestBody Role role
    ) {
        Utility utility = new Utility();
        return ResponseEntity.created(utility.setURI("/cba/roles")).body(roleService.addNewRole(role));
    }

    @PutMapping("/{roleID}")
    public void updateRole(
            @Valid
            @PathVariable("roleID") Long roleID,
            @RequestBody Role role
    ) {
        roleService.updateRole(roleID, role);
    }

    @DeleteMapping("/{roleID}")
    public void deleteRole(
            @PathVariable("roleID") Long roleID
    ) {
        roleService.deleteRole(roleID);
    }

    @PostMapping("/add-user")
    public void addUserRole(
            @Valid
            @RequestBody UserRoleDTO userRoleDTO
    ) {
        roleService.addUserRole(userRoleDTO.getUsername(), userRoleDTO.getRoleName());
    }

    @DeleteMapping("/remove-user")
    public void removedUserRole(
            @Valid
            @RequestBody UserRoleDTO userRoleDTO
    ) {
        roleService.removeUserRole(userRoleDTO.getUsername(), userRoleDTO.getRoleName());
    }
}
