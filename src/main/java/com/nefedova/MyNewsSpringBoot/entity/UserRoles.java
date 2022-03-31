package com.nefedova.MyNewsSpringBoot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_roles")
public class UserRoles {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "serial")
  private Long id;

  @Column(name = "user_id")
  private Long user_id;

  @Column(name = "role_id")
  private Long role_id;

  public UserRoles(Long user_id, Long role_id) {
    this.user_id = user_id;
    this.role_id = role_id;
  }
}
