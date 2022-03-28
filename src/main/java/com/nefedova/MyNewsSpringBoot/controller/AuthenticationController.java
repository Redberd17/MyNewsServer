package com.nefedova.MyNewsSpringBoot.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login() {
    return ResponseEntity.ok(new HashMap<>());
  }
}
