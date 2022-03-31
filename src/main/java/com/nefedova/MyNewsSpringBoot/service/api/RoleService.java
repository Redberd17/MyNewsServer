package com.nefedova.MyNewsSpringBoot.service.api;

import com.nefedova.MyNewsSpringBoot.dto.RoleDto;
import com.nefedova.MyNewsSpringBoot.entity.Role;
import java.util.List;

public interface RoleService {

  Long getRoleId(String roleName);

  List<Role> getRoles();

  Role createRole(RoleDto roleDto) throws NoSuchFieldException;

  Role getRole(String role);

  void deleteRole(Role role);
}
