package com.matteo.restfulwebservices.security;

import com.matteo.restfulwebservices.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private final UserService userDetailsService;
  private final BCryptPasswordEncoder passwordEncoder;

  public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/users")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilter(getAuthenticationFilter())
        .addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  protected AuthenticationFilter getAuthenticationFilter() throws Exception {
    final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
    filter.setFilterProcessesUrl("/users/login");
    return filter;
  }
}
