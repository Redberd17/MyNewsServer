package com.nefedova.MyNewsSpringBoot.controller;

import com.nefedova.MyNewsSpringBoot.entity.User;
import com.nefedova.MyNewsSpringBoot.service.UserService;
import java.rmi.ServerException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/getAllUsers")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> listUser = userService.getAllUsers();
    return new ResponseEntity<>(listUser, HttpStatus.OK);
  }

  @PostMapping("/createUser")
  public ResponseEntity<User> createUser(@RequestBody User newUser) throws ServerException {
    User user = userService.createUser(newUser);
    if (user == null) {
      throw new ServerException("");
    } else {
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
  }

  @PutMapping("/user")
  public ResponseEntity<User> updateUser(@RequestBody User updatedUser) throws ServerException {
    User user = userService.updateUser(updatedUser);
    if (user == null) {
      throw new ServerException("");
    } else {
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }


  @DeleteMapping("/{userId}")
  public ResponseEntity<User> deleteUserById(@PathVariable(name = "userId") Long userId) {
    userService.deleteUserById(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
