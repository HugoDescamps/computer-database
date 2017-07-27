package com.excilys.cdb.webapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select username, password, enabled from user where username=?")
		.authoritiesByUsernameQuery(
			"select username, role from user where username=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/dashboard").hasAnyRole("ADMIN", "USER")
			.antMatchers(HttpMethod.POST, "/dashboard").hasAnyRole("ADMIN")
			.antMatchers("/editComputer").hasAnyRole("ADMIN")
			.antMatchers("/addComputer").hasAnyRole("ADMIN")
			.and()
			.formLogin()
			.loginPage("/login").failureUrl("/login?error")
			.and()
			.exceptionHandling().accessDeniedPage("/accessDenied")
			.and()
			.logout().logoutSuccessUrl("/login?logout");
	}

}
