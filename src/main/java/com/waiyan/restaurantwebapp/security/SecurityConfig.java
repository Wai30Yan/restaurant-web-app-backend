package com.waiyan.restaurantwebapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.waiyan.restaurantwebapp.services.auth.AdminDetailsService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
// @CrossOrigin(origins = "https://restaurant-web-app-frontend.vercel.app")
// @CrossOrigin(origins = "http://localhost:3000")
public class SecurityConfig {

    @Autowired private JwtAuthFilter jwtAuthFilter;
    @Autowired private AdminDetailsService adminDetailsService;


    @PostConstruct
    public void init() {
        // This method will be called after the bean has been initialized
        System.out.println("\n------------------------------\n");
        System.out.println("SecurityConfig bean has been initialized.");
        System.out.println("JwtAuthFilter: " + jwtAuthFilter);
        System.out.println("AdminDetailsService: " + adminDetailsService);
        System.out.println("\n------------------------------\n");
    }

    @Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(adminDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());

		return new ProviderManager(authenticationProvider);
	}


    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http.csrf(csrf -> csrf.disable())
            .cors(cor -> cor.configure(http))
            .anonymous(anon -> anon.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(AntPathRequestMatcher.antMatcher("/guest/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/login/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**"))
                    .hasRole("ADMIN").anyRequest().authenticated())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"message\": \"Unauthorized\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{\"message\": \"Access Denied\"}");
                })
            );
        return http.build();
    }

    @Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}

