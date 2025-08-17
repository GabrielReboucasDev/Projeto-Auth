package com.developer.auth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.cors(cors -> cors
	                    .configurationSource(request -> {
	                        CorsConfiguration config = new CorsConfiguration();
	                        config.setAllowedOrigins(Arrays.asList("*"));
	                        config.setAllowedMethods(Arrays.asList("*"));
	                        config.setAllowedHeaders(Arrays.asList("*"));
	                        return config;
	                    })
	            )
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authoriza -> authoriza
						.requestMatchers("/swagger-ui/**").permitAll()
	                    .requestMatchers("/v3/api-docs/**").permitAll()
	                    .requestMatchers(HttpMethod.GET, "/funcionarios/lista").hasRole("ADMIN") 
						.requestMatchers(HttpMethod.GET, "/usuarios/admin").hasRole("ADMIN") 
						.requestMatchers(HttpMethod.GET, "/usuarios/user").hasRole("USER") 
						.requestMatchers(HttpMethod.POST, "/usuarios/criarUsuario").permitAll() 
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/auth/logout").permitAll() 
						.requestMatchers(HttpMethod.POST, "/auth/refresh-token").permitAll() 
						.anyRequest().authenticated()
						)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
