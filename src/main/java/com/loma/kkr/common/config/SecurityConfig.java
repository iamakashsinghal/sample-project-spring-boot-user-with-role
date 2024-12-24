package com.loma.kkr.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.loma.kkr.common.service.impl.CustomUserDetailsServiceImpl;

/**
 * This Configuration class is related to Web Spring Security configure
 * 
 * @author Akash
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsServiceImpl userDetailsServiceImpl;
	private final JwtAuthorizationFilter jwtAuthorizationFilter;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Value("${security.enable-csrf}")
	private boolean csrfEnabled;

	public SecurityConfig(CustomUserDetailsServiceImpl userDetailsServiceImpl,
			JwtAuthorizationFilter jwtAuthorizationFilter) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.jwtAuthorizationFilter = jwtAuthorizationFilter;

	}

	/**
	 * To Authentication to user details
	 * 
	 * @param http
	 * @return authenticationManagerBuilder
	 */
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

	/**
	 * To Security Filter Chain which role have access api
	 * @param HttpSecurity http
	 * @return http
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		if (!csrfEnabled) {

			http.csrf().disable().authorizeRequests().requestMatchers("/api/**").permitAll()
					// .requestMatchers("/api/user/**").hasRole("SA").requestMatchers("/api/user/userList/").hasRole("MA")
					// .requestMatchers("/api/role/addRole/").hasRole("MA")
					.anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		}

		return http.build();
	}

	/**
	 * Password Encoded which is provide Password Encoder Factories class
	 * 
	 * @return Encoded Password
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
