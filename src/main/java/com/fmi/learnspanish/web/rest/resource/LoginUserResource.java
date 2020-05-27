package com.fmi.learnspanish.web.rest.resource;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserResource {

  @NotNull
  @Email
  private String email;

  @NotNull
  private String password;

}
