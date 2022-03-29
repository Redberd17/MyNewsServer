package com.nefedova.MyNewsSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseModel {

  private String username;
  private String token;
}
