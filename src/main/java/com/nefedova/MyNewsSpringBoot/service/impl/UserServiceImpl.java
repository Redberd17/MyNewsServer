package com.nefedova.MyNewsSpringBoot.service.impl;

import com.nefedova.MyNewsSpringBoot.dto.UserDto;
import com.nefedova.MyNewsSpringBoot.entity.User;
import com.nefedova.MyNewsSpringBoot.repository.UserRepository;
import com.nefedova.MyNewsSpringBoot.service.api.UserService;
import com.nefedova.MyNewsSpringBoot.utils.BCryptEncoderUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getUser(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User createUser(UserDto userDto) throws NoSuchFieldException {
    User user = new User();
    if (userDto.getUsername() == null || userDto.getPassword() == null) {
      throw new NoSuchFieldException("Username or password is empty");
    }
    user.setPassword(BCryptEncoderUtil.encryptPassword(userDto.getPassword()));
    user.setUsername(userDto.getUsername());
    return userRepository.save(user);
  }

  @Override
  public void deleteUser(String username) {
    userRepository.deleteByUsername(username);
  }
}
