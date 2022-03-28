package com.nefedova.MyNewsSpringBoot.security.jwt;

import com.nefedova.MyNewsSpringBoot.entity.Role;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${jwt.token.secret}")
  private String secret;

  @Value("${jwt.token.expired}")
  private Long expiredInMilliseconds;

  public String createToken(String username, List<Role> roles) {
    return null;
  }

  public Authentication getAuthentication(String token) {
    return null;
  }

  public String getUsername() {
    return null;
  }

  public boolean validateToken(String token) {
    return false;
  }

  private List<String> getRoleNames(List<Role> roles) {
    List<String> roleNames = new ArrayList<>();
    roles.forEach(role -> roleNames.add(role.getRole()));
    return roleNames;
  }
}
