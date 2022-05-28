package org.pfe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
		
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean DaoAuthenticationProvider AuthProvider() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService());
		return authProvider;
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(AuthProvider());
		
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/password/forgotPassword").permitAll().antMatchers("/filieres/**").hasAnyAuthority("ADMIN").
		antMatchers("/reset_password/**").permitAll().
		antMatchers("/users/**").hasAnyAuthority("ADMIN").
		antMatchers("/cours").hasAnyAuthority("PROFESSEUR").antMatchers("/FormCours").hasAnyAuthority("PROFESSEUR").
		antMatchers("/reclamationsProf").hasAnyAuthority("PROFESSEUR").antMatchers("/reponse/**").hasAnyAuthority("PROFESSEUR").antMatchers("/finModuleProf/{id}").hasAnyAuthority("PROFESSEUR").
		antMatchers("/departements").hasAnyAuthority("ADMIN").
		antMatchers("/cycle/**").hasAnyAuthority("ADMIN").
		anyRequest().
		authenticated().
		and().
		formLogin().permitAll().loginPage("/login")
		.usernameParameter("email").successHandler(successHandler).
		and().logout().permitAll();
		
	
	
	}		
	 @Autowired private LoginSuccessHandler successHandler;
	
	

}
