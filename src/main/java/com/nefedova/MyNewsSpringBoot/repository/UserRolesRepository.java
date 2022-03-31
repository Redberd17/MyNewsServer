package com.nefedova.MyNewsSpringBoot.repository;

import com.nefedova.MyNewsSpringBoot.entity.UserRoles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserRolesRepository extends CrudRepository<UserRoles, Long> {

}
