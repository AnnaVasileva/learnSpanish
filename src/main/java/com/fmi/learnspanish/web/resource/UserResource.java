// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.web.resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResource {

  private String username;

  private String email;

  private String password;

  private String salt;

  private Integer grammarLevel;

  private String vocabularyLevel;

  private String practiceLevel;

}
