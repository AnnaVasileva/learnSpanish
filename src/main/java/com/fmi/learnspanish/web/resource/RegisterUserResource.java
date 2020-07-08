package com.fmi.learnspanish.web.resource;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserResource {

  @NotNull
  private String username;

  @NotNull
  @Email
  private String email;

  @NotNull
  private String password;

  @NotNull
  private String confirmPassword;

  private Set<RoleServiceModel> authorities;

}
