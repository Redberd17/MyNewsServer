package com.nefedova.MyNewsSpringBoot.controller;

import static java.lang.String.format;

import com.nefedova.MyNewsSpringBoot.dto.RoleDto;
import com.nefedova.MyNewsSpringBoot.entity.Role;
import com.nefedova.MyNewsSpringBoot.entity.User;
import com.nefedova.MyNewsSpringBoot.service.api.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/roles")
public class RoleController {

  private final RoleService roleService;

  @Autowired
  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<Role>> getAllRoles() {
    List<Role> roles = roleService.getRoles();
    if (CollectionUtils.isEmpty(roles)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(roles, HttpStatus.OK);
  }

  @PostMapping("/role")
  public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
    Role role = roleService.getRole(roleDto.getRole());
    if (role != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          format("Role %s is already exist", roleDto.getRole()));
    }
    try {
      Role createdRole = roleService.createRole(roleDto);
      if (createdRole == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role had not been created");
      }
      return new ResponseEntity<>(RoleDto.roleToDto(createdRole), HttpStatus.CREATED);
    } catch (NoSuchFieldException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role had not been created");
    }
  }

  @DeleteMapping("/{role}")
  public ResponseEntity<User> deleteUser(@PathVariable(name = "role") String roleName) {
    Role role = roleService.getRole(roleName);
    if (role == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    roleService.deleteRole(role);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
