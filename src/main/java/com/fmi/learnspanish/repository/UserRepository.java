// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.repository;

import com.fmi.learnspanish.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  boolean existsByEmail(String email);

  User findByEmail(String email);

  User findByUsername(String username);
  
  User findByUsernameAndEmail(String username, String email);
  
  List<User> findAllByOrderByUsername();

}
