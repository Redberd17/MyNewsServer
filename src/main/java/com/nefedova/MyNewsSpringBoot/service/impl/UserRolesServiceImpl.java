package com.nefedova.MyNewsSpringBoot.service.impl;

import com.nefedova.MyNewsSpringBoot.entity.UserRoles;
import com.nefedova.MyNewsSpringBoot.repository.UserRolesRepository;
import com.nefedova.MyNewsSpringBoot.service.api.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRolesServiceImpl implements UserRolesService {

  private final UserRolesRepository userRolesRepository;

  @Autowired
  public UserRolesServiceImpl(UserRolesRepository userRolesRepository) {
    this.userRolesRepository = userRolesRepository;
  }

  @Override
  public UserRoles createUserRoles(UserRoles userRoles) {
    return userRolesRepository.save(userRoles);
  }
}
