package com.explorer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.explorer.services.AuthenticationErrorHandler;
import com.explorer.services.CustomUserDetailsService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final CustomUserDetailsService userDetailsService;
	private final CustomJwtFilter customJwtFilter;
	private final PasswordEncoder passwordEncoder;

	public SecurityConfiguration(CustomUserDetailsService userDetailsService, CustomJwtFilter customJwtFilter,
			PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.customJwtFilter = customJwtFilter;
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(
						"/", 
						"/viaggi/**",
						"/registrazione", 
						"/index", 
						"/api/**",
						"/css/**", 
						"/js/**", 
						"/img/**",
						"/images/**",
						"viaggiContinente/**", 
						"api/add/**", 
						"api/viaggi/**",
						"viaggio/**",
						"/profilo",
						"/risultati",
						"api/viaggi/partecipanti/{idViaggio}"
				).permitAll()
						// .requestMatchers(HttpMethod.POST, "/api/ordini").permitAll()
						// .requestMatchers(HttpMethod.GET, "/api/ordini").hasAnyRole("ADMIN","USER")
						.requestMatchers("/swagger-ui/**", 
								"/swagger-ui.html", 
								"/v3/api-docs.yaml", 
								"/v3/api-docs/**",
								"/api/v1/auth/**",
								"api/utente/codice/**",
								"api/utente/nominativo/**"
								)
						.hasAnyRole("ADMIN", "USER").anyRequest().authenticated())
				.formLogin(formLogin -> formLogin.loginPage("/login").permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true)
						.deleteCookies("token").permitAll())
				.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exceptionHandling -> exceptionHandling
						.authenticationEntryPoint((request, response, authException) -> {
							try {
								AuthenticationErrorHandler.handleAuthenticationError(request, response, authException);
							} catch (IOException | ServletException e) {
								e.printStackTrace();
							}
						}));

		return http.build();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}