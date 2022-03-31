package com.nefedova.MyNewsSpringBoot.controller;

import static java.lang.String.format;

import com.nefedova.MyNewsSpringBoot.dto.UserDto;
import com.nefedova.MyNewsSpringBoot.entity.User;
import com.nefedova.MyNewsSpringBoot.entity.UserRoles;
import com.nefedova.MyNewsSpringBoot.service.api.RoleService;
import com.nefedova.MyNewsSpringBoot.service.api.UserRolesService;
import com.nefedova.MyNewsSpringBoot.service.api.UserService;
import com.nefedova.MyNewsSpringBoot.utils.RoleEnum;
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
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final UserRolesService userRolesService;
  private final RoleService roleService;

  @Autowired
  public UserController(UserService userService,
      UserRolesService userRolesService,
      RoleService roleService) {
    this.userService = userService;
    this.userRolesService = userRolesService;
    this.roleService = roleService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> listUser = userService.getAllUsers();
    if (CollectionUtils.isEmpty(listUser)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(listUser, HttpStatus.OK);
  }

  @GetMapping("/{username}")
  public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
    User user = userService.getUser(username);
    if (user != null) {
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }


  @PostMapping("/user")
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
    User user = userService.getUser(userDto.getUsername());
    if (user != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          format("User with username %s is already exist", userDto.getUsername()));
    }
    try {
      Long roleId = roleService.getRoleId(RoleEnum.USER.getRole());
      if (roleId == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role is not exist");
      }
      User createdUser = userService.createUser(userDto);
      if (createdUser == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User had not been created");
      }
      UserRoles userRoles = new UserRoles(createdUser.getUserId(), roleId);
      userRolesService.createUserRoles(userRoles);
      return new ResponseEntity<>(UserDto.userToDto(createdUser), HttpStatus.CREATED);
    } catch (NoSuchFieldException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User had not been created");
    }
  }

  @DeleteMapping("/{username}")
  public ResponseEntity<User> deleteUser(@PathVariable(name = "username") String username) {
    User user = userService.getUser(username);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    userService.deleteUser(username);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
