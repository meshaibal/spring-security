package com.shaibal.spring.security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.shaibal.spring.security.config.CustomAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityBaseConfig extends WebSecurityConfigurerAdapter{

	static final String[] PUBLIC_ANT_MATCHERS = {
            "/swagger-ui.html", "/docs/index.html", "/v2/api-docs",
            "/swagger-resources/**", "/configuration/*", "/error/*",
            "/webjars/springfox-swagger-ui/**"};
	
	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		    .withUser("username")
		    .password("password")
		    .roles("USER");
		   
	}*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(PUBLIC_ANT_MATCHERS).permitAll()
		    .antMatchers("/login/**").permitAll()
		    .antMatchers("/message/**").hasAuthority("USER")
		    .anyRequest().authenticated()
		    .and()
		    .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
	}
}
