﻿package pl.printo3d.waluty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

  @Bean
  public PasswordEncoder passwordEncoder() 
  {
      return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService()
  {
    UserDetails userDetails = User.withDefaultPasswordEncoder()
    .username("kloc")
    .password("kloc")
    .roles("KIEP")
    .build();

    return new InMemoryUserDetailsManager(userDetails);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
    .antMatchers("/login","/img/**","/css/**").permitAll()
    .antMatchers("/register", "/img/**","/css/**").permitAll()
    .antMatchers("/").permitAll()
    .anyRequest().hasRole("KIEP");


    http.formLogin().permitAll()
      .loginPage("/login").permitAll()
      .defaultSuccessUrl("/", true)
      .and()
      .logout().permitAll()
      .logoutSuccessUrl("/")
      .deleteCookies("JSESSIONID");

    http.csrf().disable();

  }
}
