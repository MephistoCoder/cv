package com.foxminded.bohdansharubin.universitycms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.foxminded.bohdansharubin.universitycms.security.UrlAuthenticationSuccessHandler;
import com.foxminded.bohdansharubin.universitycms.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserService userDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UrlAuthenticationSuccessHandler urlAuthenticationSuccessHandler;
	
	@Bean 
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	   	http.csrf()
	   		.disable()
	        .authorizeRequests()
	        .antMatchers("/anonymous*")
	        .anonymous()
	        .antMatchers("/css/**", "/webjars/**", "/login").permitAll()
	        .anyRequest()
	        .authenticated()
	        .and()
	        .formLogin()
	        .loginPage("/login")
	        .loginProcessingUrl("/perform_login")
	        .defaultSuccessUrl("/", true)
	        .successHandler(urlAuthenticationSuccessHandler)
	        .and()
	        .logout()
	        .logoutUrl("/perform_logout")
	        .deleteCookies("JSESSIONID");
	    return http.build();
	}
	    
	@Bean
	public UserDetailsManager users(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
			.userDetailsService(userDetailsService)
	        .passwordEncoder(passwordEncoder())
	        .and()
	        .authenticationProvider(authenticationProvider())
	        .build();

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
	    jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
	    return jdbcUserDetailsManager;
	}
	    
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}

}
