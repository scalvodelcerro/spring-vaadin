package com.example.springvaadin;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable(); // Use Vaadin's CSRF protection
//            .authorizeRequests().anyRequest().authenticated() // User must be authenticated to access any part of the application
//            .and()
//            .formLogin().loginPage("/login").permitAll() // Login page is accessible to anybody
//            .and()
//            .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logged-out").permitAll() // Logout success page is accessible to anybody
//            .and()
//            .sessionManagement().sessionFixation().newSession(); // Create completely new session
    }
}
