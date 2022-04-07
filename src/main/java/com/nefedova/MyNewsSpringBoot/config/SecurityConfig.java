package com.nefedova.MyNewsSpringBoot.config;

import com.nefedova.MyNewsSpringBoot.security.jwt.JwtConfigurer;
import com.nefedova.MyNewsSpringBoot.security.jwt.JwtTokenProvider;
import com.nefedova.MyNewsSpringBoot.utils.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String LOGIN_ENDPOINT = "/auth/login";
  private static final String REGISTRATION_ENDPOINT = "/users/user";
  private static final String ROLES_ENDPOINT = "/roles/role";
  private static final String ALL_USERS_ENDPOINT = "/users/all";
  private static final String SET_ROLE_ENDPOINT = "/users/role";
  private static final String DELETE_USER_ENDPOINT = "/users/**";

  private final JwtTokenProvider jwtTokenProvider;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Autowired
  public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(LOGIN_ENDPOINT, REGISTRATION_ENDPOINT, ROLES_ENDPOINT).permitAll()
        .antMatchers(ALL_USERS_ENDPOINT, SET_ROLE_ENDPOINT).hasAuthority(RoleEnum.ADMIN.getRole())
        .antMatchers(HttpMethod.DELETE, DELETE_USER_ENDPOINT).hasAuthority(RoleEnum.ADMIN.getRole())
        .anyRequest().authenticated()
        .and()
        .apply(new JwtConfigurer(jwtTokenProvider));
  }
}
