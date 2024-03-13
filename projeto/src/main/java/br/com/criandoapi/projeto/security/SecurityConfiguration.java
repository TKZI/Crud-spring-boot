package br.com.criandoapi.projeto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {
	
	@Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       
		http.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests((authorize) -> authorize.requestMatchers(HttpMethod.POST , "/usuarios/senha")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
				.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults())
		.formLogin(Customizer.withDefaults());
		
		http.addFilterBefore(new SecurityFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
