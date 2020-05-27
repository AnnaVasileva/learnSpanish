package com.fmi.learnspanish.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public interface PasswordEncoderService {

  Map<String, String> encode(String planePassword) throws NoSuchAlgorithmException, InvalidKeySpecException;

  String encode(String planePassword, String salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException;

}
