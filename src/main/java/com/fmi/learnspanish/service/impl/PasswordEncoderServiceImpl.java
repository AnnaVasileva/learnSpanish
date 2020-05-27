package com.fmi.learnspanish.service.impl;

import com.fmi.learnspanish.service.PasswordEncoderService;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@Service
public class PasswordEncoderServiceImpl implements PasswordEncoderService {
  private static final String PLACEHOLDER_PASSWORD = "password";
  private static final String PLACEHOLDER_SALT = "salt";

  @Override
  public Map<String, String> encode(String planePassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecureRandom random = new SecureRandom();
    byte[] saltBytes = new byte[16];
    random.nextBytes(saltBytes);

    String saltString = Base64.encodeBase64String(saltBytes);

    KeySpec spec = new PBEKeySpec(planePassword.toCharArray(), saltBytes, 65536, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] passwordHash = factory.generateSecret(spec).getEncoded();

    Map<String, String> map = new HashMap<>();
    map.put(PLACEHOLDER_PASSWORD, java.util.Base64.getEncoder().encodeToString(passwordHash));
    map.put(PLACEHOLDER_SALT, saltString);
    return map;
  }

  @Override
  public String encode(String planePassword, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

    byte[] saltBytes = Base64.decodeBase64(salt);

    KeySpec spec = new PBEKeySpec(planePassword.toCharArray(), saltBytes, 65536, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] passwordHash = factory.generateSecret(spec).getEncoded();

    return java.util.Base64.getEncoder().encodeToString(passwordHash);

  }

}
