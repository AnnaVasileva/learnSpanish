package com.fmi.learnspanish.config;

import com.fmi.learnspanish.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserService userService;

  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http//
        .csrf().disable()//
        .authorizeRequests()//
        .antMatchers("/").permitAll()//
        .antMatchers("/favicon.ico", "/css/*", "/img/*").permitAll()//
        .antMatchers("/index", "/about", "/users/register").permitAll()//
        .anyRequest().authenticated()//
        .and()//
        .formLogin().loginPage("/users/login").permitAll()// in case of issues try with "/login"
        .usernameParameter("username")//
        .usernameParameter("password")//
        .defaultSuccessUrl("/home", true)//
        .successHandler(authenticationSuccessHandler)
        .and()//
        .logout().logoutSuccessUrl("/").permitAll()//
        .and()//
        .exceptionHandling().accessDeniedPage("/unauthorized");
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    return userService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return passwordEncoder;
  }

  @Autowired
  public void configureGloadal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
  }
}
