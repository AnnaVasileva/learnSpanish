package com.fmi.learnspanish.service.impl;

import com.fmi.learnspanish.domain.Role;
import com.fmi.learnspanish.repository.RoleRepository;
import com.fmi.learnspanish.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public void seedRolesInDb() {
    roleRepository.saveAndFlush(new Role("ADMIN"));
    roleRepository.saveAndFlush(new Role("USER"));
  }

}
