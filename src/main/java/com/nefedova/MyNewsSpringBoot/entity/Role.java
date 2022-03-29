package com.nefedova.MyNewsSpringBoot.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {

  @Id
  @Column(name = "role_id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long roleId;

  @Column(name = "role")
  private String role;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  private List<User> users;
}
