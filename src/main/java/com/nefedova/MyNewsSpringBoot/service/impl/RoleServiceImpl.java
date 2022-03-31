package com.nefedova.MyNewsSpringBoot.service.impl;

import com.nefedova.MyNewsSpringBoot.dto.RoleDto;
import com.nefedova.MyNewsSpringBoot.entity.Role;
import com.nefedova.MyNewsSpringBoot.repository.RoleRepository;
import com.nefedova.MyNewsSpringBoot.service.api.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Autowired
  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Long getRoleId(String roleName) {
    Role role = roleRepository.findByRole(roleName);
    return role != null ? role.getRoleId() : null;
  }

  @Override
  public List<Role> getRoles() {
    return roleRepository.findAll();
  }

  @Override
  public Role createRole(RoleDto roleDto) throws NoSuchFieldException {
    if (roleDto.getRole() == null) {
      throw new NoSuchFieldException("Role is empty");
    }
    Role role = new Role();
    role.setRole(roleDto.getRole());
    return roleRepository.save(role);
  }

  @Override
  public Role getRole(String role) {
    return roleRepository.findByRole(role);
  }

  @Override
  public void deleteRole(Role role) {
    roleRepository.delete(role);
  }
}
