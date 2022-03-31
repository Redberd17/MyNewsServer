package com.nefedova.MyNewsSpringBoot.service.api;

import com.nefedova.MyNewsSpringBoot.dto.UserDto;
import com.nefedova.MyNewsSpringBoot.entity.User;
import java.util.List;

public interface UserService {

  List<User> getAllUsers();

  User getUser(String username);

  User createUser(UserDto userDto) throws NoSuchFieldException;

  void deleteUser(String username);

}
