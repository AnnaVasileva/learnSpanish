package com.fmi.learnspanish.service.impl;

import com.fmi.learnspanish.domain.User;
import com.fmi.learnspanish.repository.UserRepository;
import com.fmi.learnspanish.service.AuthService;
import com.fmi.learnspanish.service.PasswordEncoderService;
import com.fmi.learnspanish.web.rest.resource.LoginUserResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
  private static final String PLACEHOLDER_PASSWORD = "password";
  private static final String PLACEHOLDER_SALT = "salt";

  @Autowired
  private PasswordEncoderService passwordEncoderService;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void register(User user) throws Exception {

    // Map<String, String> pbkdf2EncodedMap = new HashMap<>();
    // try {
    // pbkdf2EncodedMap = passwordEncoderService.encode(user.getPassword());
    // } catch (Exception e) {
    // throw new PasswordEncoderException("Encoding issue occured.");
    // }
    //
    // user.setPassword(pbkdf2EncodedMap.get(PLACEHOLDER_PASSWORD));
    // user.setSalt(pbkdf2EncodedMap.get(PLACEHOLDER_SALT));

    log.info("User {} was successfully registred.", user.getUsername());
    userRepository.saveAndFlush(user);
  }

  @Override
  public User login(LoginUserResource registerUserResource) throws Exception {

    User user = userRepository.findByEmail(registerUserResource.getEmail());

    if (Objects.isNull(user)) {
      log.error("User with email: {}, was not found.", registerUserResource.getEmail());
      throw new Exception();
    }

    // String encodedPassword = null;
    // try {
    // encodedPassword = passwordEncoderService.encode(registerUserResource.getPassword(), user.getSalt());
    // } catch (Exception e) {
    // throw new PasswordEncoderException("Encoding issue occured.");
    // }
    //
    // if (!user.getPassword().equals(encodedPassword)) {
    // log.error("Passwords don't match");
    // throw new Exception();
    // }

    return user;
  }

}
