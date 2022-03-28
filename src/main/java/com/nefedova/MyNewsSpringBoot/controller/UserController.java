package com.nefedova.MyNewsSpringBoot.controller;

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
    User user = userService.createUser(newUser);
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(HttpStatus.CREATED);
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
    userService.deleteUser(username);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
