package com.nefedova.MyNewsSpringBoot.controller;

import static java.lang.String.format;

import com.nefedova.MyNewsSpringBoot.entity.User;
import com.nefedova.MyNewsSpringBoot.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> listUser = userService.getAllUsers();
    if (CollectionUtils.isEmpty(listUser)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(listUser, HttpStatus.OK);
  }

  @PostMapping("/{username}")
  public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
    User user = userService.getUser(username);
    if (user != null) {
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/user")
  public ResponseEntity<User> createUser(@RequestBody User newUser) {
    User user = userService.getUser(newUser.getUsername());
    if (user != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          format("User with username %s is already exist", newUser.getUsername()));
    }
    User createdUser = userService.createUser(newUser);
    if (createdUser == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has not been created");
    } else {
      return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
  }

  @PutMapping("/{username}")
  public ResponseEntity<User> updateUser(@PathVariable String username,
      @RequestBody User updatedUser) {
    User user = userService.updateUser(updatedUser);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }


  @DeleteMapping("/{username}")
  public ResponseEntity<User> deleteUserById(@PathVariable(name = "username") String username) {
    User user = userService.getUser(username);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    try {
      userService.deleteUser(username);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
