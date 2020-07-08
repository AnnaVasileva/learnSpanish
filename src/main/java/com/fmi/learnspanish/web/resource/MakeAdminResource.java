package com.fmi.learnspanish.web.resource;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MakeAdminResource {

	  @NotNull
	  private String username;

	  @NotNull
	  @Email
	  private String email;
}
