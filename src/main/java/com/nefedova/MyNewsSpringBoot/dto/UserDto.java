package com.nefedova.MyNewsSpringBoot.dto;

import com.nefedova.MyNewsSpringBoot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  String username;
  String password;

  public static UserDto userToDto(User user) {
    return new UserDto(user.getUsername(), user.getPassword());
  }
}
