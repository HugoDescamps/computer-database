package com.excilys.cdb.webapp.config;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.excilys.cdb.core.User;
import com.excilys.cdb.persistence.config.HibernateConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

		List<User> usersList;

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {
			Query<User> usersListQuery = session.createQuery("FROM User", User.class);

			usersList = usersListQuery.list();
		}

		for (User user : usersList) {
			auth.inMemoryAuthentication().withUser(user.getLogin()).password(user.getPassword())
					.roles(user.getRole().name());
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/dashboard").hasAnyRole("ADMIN", "USER")
				.antMatchers(HttpMethod.POST, "/dashboard").hasAnyRole("ADMIN").antMatchers("/editComputer")
				.hasAnyRole("ADMIN").antMatchers("/addComputer").hasAnyRole("ADMIN").and().formLogin()
				.loginPage("/login").failureUrl("/login?error").and().exceptionHandling()
				.accessDeniedPage("/accessDenied").and().logout().logoutSuccessUrl("/login?logout");
	}

}
