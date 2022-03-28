package com.nefedova.MyNewsSpringBoot.service;

import com.nefedova.MyNewsSpringBoot.entity.User;
import java.util.List;

public interface UserService {

  List<User> getAllUsers();

  User getUser(String username);

  User createUser(User newUser);

  User updateUser(User user);

  void deleteUser(String username);

}
