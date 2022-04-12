package com.nefedova.MyNewsSpringBoot.security.jwt;

import com.nefedova.MyNewsSpringBoot.entity.Role;
import com.nefedova.MyNewsSpringBoot.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {

  public static JwtUser create(User user) {
    return new JwtUser(
            user.getUserId(),
            user.getUsername(),
            user.getPassword(),
            true,
            mapRolesToGrantedAuthorities(new ArrayList<>(user.getRoles()))
    );
  }

  private static List<GrantedAuthority> mapRolesToGrantedAuthorities(List<Role> roles) {
    return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getRole()))
            .collect(Collectors.toList());
  }

}
