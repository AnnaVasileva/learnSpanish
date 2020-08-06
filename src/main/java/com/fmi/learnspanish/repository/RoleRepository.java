// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.repository;

import com.fmi.learnspanish.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  Role findByAuthority(String auth);
}
