package com.nefedova.MyNewsSpringBoot.service;

import com.nefedova.MyNewsSpringBoot.entity.User;
import com.nefedova.MyNewsSpringBoot.repository.UserRepository;
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
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User updateUser(User user) {
    User updatedUser = userRepository.findById(user.getUserId()).get();
    updatedUser.setUsername(user.getUsername());
    updatedUser.setPassword(user.getPassword());
    updatedUser.setRole(user.getRole());
    userRepository.save(updatedUser);
    return updatedUser;
  }

  @Override
  public void deleteUserById(Long id) {
    userRepository.deleteByUserId(id);
  }

}
