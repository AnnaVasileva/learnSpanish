// All Rights Reserved, Copyright © Anna Vasileva 2020.

package com.fmi.learnspanish.service;

import java.util.List;

public interface AuthenticatedUserService {

  String getUsername();
  
  List<String> getRoles();
}
