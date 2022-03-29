package com.nefedova.MyNewsSpringBoot.security.jwt;

import com.nefedova.MyNewsSpringBoot.entity.Role;
import com.nefedova.MyNewsSpringBoot.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${jwt.token.secret}")
  private String secret;

  @Value("${jwt.token.expired}")
  private Long expiredInMilliseconds;

  private UserDetailsService userDetailsService;

  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public String createToken(String username, List<Role> roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("roles", getRoleNames(roles));
    Date currentDate = new Date();
    Date validity = new Date(currentDate.getTime() + expiredInMilliseconds);
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(currentDate)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.ES256, secret)
        .compact();
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(Constants.AUTHORIZATION);
    if (bearerToken != null && bearerToken.startsWith(Constants.BEARER)) {
      return bearerToken.substring(Constants.BEARER.length());
    }
    return null;
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      throw new JwtAuthException("JWT token is expired or invalid");
    }
  }

  private List<String> getRoleNames(List<Role> roles) {
    List<String> roleNames = new ArrayList<>();
    roles.forEach(role -> roleNames.add(role.getRole()));
    return roleNames;
  }
}
