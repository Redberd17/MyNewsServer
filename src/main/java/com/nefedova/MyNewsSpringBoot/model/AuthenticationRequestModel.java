package com.nefedova.MyNewsSpringBoot.model;

import lombok.Data;

@Data
public class AuthenticationRequestModel {

  private String username;
  private String password;
}
