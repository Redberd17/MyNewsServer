package com.nefedova.MyNewsSpringBoot.service;

import com.nefedova.MyNewsSpringBoot.entity.User;
import com.nefedova.MyNewsSpringBoot.repository.UserRepository;
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
  public User createUser(User user) {
    user.setPassword(BCryptEncoderUtil.encryptPassword(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public User updateUser(User user) {
    User updatingUser = getUser(user.getUsername());
    if (updatingUser == null) {
      return null;
    }
    updatingUser.setUsername(user.getUsername());
    updatingUser.setPassword(user.getPassword());
    updatingUser.setRoles(user.getRoles());
    return userRepository.save(updatingUser);
  }

  @Override
  public void deleteUser(String username) {
    userRepository.deleteByUsername(username);
  }
}
