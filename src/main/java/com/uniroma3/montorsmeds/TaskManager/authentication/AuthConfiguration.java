package com.uniroma3.montorsmeds.TaskManager.authentication;

import javax.sql.DataSource;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.uniroma3.montorsmeds.TaskManager.model.Credentials.ADMIN_ROLE;

public class AuthConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/", "/index", "/login", "/users/register").permitAll()
			.antMatchers(HttpMethod.POST, "/login", "/users/register").permitAll()
			.antMatchers(HttpMethod.GET, "/admin").hasAnyAuthority(ADMIN_ROLE)
			.anyRequest().authenticated()
			.and().formLogin()
			.defaultSuccessUrl("/home/")
			.and().logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/index");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
			.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}
	
}
