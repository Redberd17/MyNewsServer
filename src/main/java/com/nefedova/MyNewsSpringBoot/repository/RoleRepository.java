package com.nefedova.MyNewsSpringBoot.repository;

import com.nefedova.MyNewsSpringBoot.entity.Role;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

  Role findByRole(String role);

  List<Role> findAll();
}
