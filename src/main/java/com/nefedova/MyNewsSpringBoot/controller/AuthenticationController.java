package com.nefedova.MyNewsSpringBoot.controller;

import static java.lang.String.format;

import com.nefedova.MyNewsSpringBoot.entity.User;
import com.nefedova.MyNewsSpringBoot.model.AuthenticationRequestModel;
import com.nefedova.MyNewsSpringBoot.model.AuthenticationResponseModel;
import com.nefedova.MyNewsSpringBoot.security.jwt.JwtTokenProvider;
import com.nefedova.MyNewsSpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  private final UserService userService;

  @Autowired
  public AuthenticationController(
      AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseModel> login(
      @RequestBody AuthenticationRequestModel authenticationRequestModel) {
    try {
      String username = authenticationRequestModel.getUsername();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
          authenticationRequestModel.getPassword()));
      User user = userService.getUser(username);
      if (user == null) {
        throw new UsernameNotFoundException(format("User with username: %s not found", username));
      }
      String token = jwtTokenProvider.createToken(username, user.getRoles());
      return new ResponseEntity<>(new AuthenticationResponseModel(username, token), HttpStatus.OK);
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid credentials");
    }
  }
}
