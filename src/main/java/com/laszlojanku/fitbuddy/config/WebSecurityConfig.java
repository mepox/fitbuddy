package com.laszlojanku.fitbuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * WebSecurity configuration class where we set up our SecurityFilterChain. 
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		// User's page
		http.authorizeRequests().antMatchers("/user/**").hasRole("USER");
		
		// Admin's page
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		
		// H2 Console
		http.authorizeRequests().antMatchers("/console/**").permitAll();
		http.headers().frameOptions().sameOrigin();
		
		// URLs does not require login		
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/login/perform_login").permitAll();
		http.authorizeRequests().antMatchers("/logout").permitAll();
		http.authorizeRequests().antMatchers("/register").permitAll();
		http.authorizeRequests().antMatchers("/version").permitAll();
		http.authorizeRequests().antMatchers("/public/**").permitAll();		
		
		// Access denied for Admin's page 
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		// Configure custom login form
		http.authorizeRequests().and().formLogin()
				.loginPage("/login"); 		// Sets the login page URL				
		
		http.authorizeRequests().anyRequest().authenticated();
		
		return http.build();
	}

}