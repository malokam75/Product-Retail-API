package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filter.JwtRequestFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Bean
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/authenticate").permitAll()
			.antMatchers(HttpMethod.POST, "/api/user").permitAll()
			.antMatchers(HttpMethod.GET, "/api/user").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/user").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/book").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/api/book").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/book").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/book/count").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/book").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.GET, "/api/book/id").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.GET, "/api/book/title").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.GET, "/api/book/genre").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.GET, "/api/book/pages/{pages}").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.GET, "/api/book/medium/{medium}").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.GET, "/api/book/author/age/{age}").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.GET, "/api/book/author/name/{name}").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.GET, "/api/book/author/gender/{gender}").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.GET, "/api/book/author/published/{published}").hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.GET, "/api/order").hasAnyRole("USER")
			.antMatchers(HttpMethod.GET, "/api/order/id").hasAnyRole("USER")
			.antMatchers(HttpMethod.GET, "/api/order/book").hasAnyRole("USER")
			.antMatchers(HttpMethod.GET, "/api/order/cart").hasAnyRole("USER")
			.antMatchers(HttpMethod.GET, "/api/order/user").hasAnyRole("USER")
			.antMatchers(HttpMethod.PUT, "/api/order").hasAnyRole("USER")
			.antMatchers(HttpMethod.POST, "/api/order").hasAnyRole("USER")
			.antMatchers(HttpMethod.DELETE, "/api/order").hasAnyRole("USER")
			.antMatchers(HttpMethod.GET, "/api/cart").hasAnyRole("USER")
			.antMatchers(HttpMethod.GET, "/api/cart/count").hasAnyRole("USER")
			.antMatchers(HttpMethod.PUT, "/api/cart").hasAnyRole("USER")
			.antMatchers(HttpMethod.POST, "/api/cart").hasAnyRole("USER")
			.antMatchers(HttpMethod.DELETE, "/api/cart").hasAnyRole("USER")
			.antMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
			.antMatchers(HttpMethod.GET, "/swagger-ui/index.html").permitAll()
//			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
		
		http.addFilterBefore( jwtRequestFilter, UsernamePasswordAuthenticationFilter.class );
		
		return http.build();
	}
	
	@Bean
	protected PasswordEncoder encoder() {
//		return NoOpPasswordEncoder.getInstance();
				
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder( encoder() );
		
		return authProvider;
	}
	
	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
}
