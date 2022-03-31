package com.nefedova.MyNewsSpringBoot.dto;

import com.nefedova.MyNewsSpringBoot.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

  String role;

  public static RoleDto roleToDto(Role role) {
    return new RoleDto(role.getRole());
  }
}
