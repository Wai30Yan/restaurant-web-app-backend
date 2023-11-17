package com.waiyan.restaurantwebapp.security;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain; 
import jakarta.servlet.ServletException; 
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; 
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import com.waiyan.restaurantwebapp.services.auth.AdminDetailsService;
import com.waiyan.restaurantwebapp.services.auth.JwtService;

import java.io.IOException;

@Component
// @CrossOrigin(origins = "https://restaurant-web-app-frontend.vercel.app")
// @CrossOrigin(origins = "http://localhost:3000")
public class JwtAuthFilter extends OncePerRequestFilter { 

    @Autowired private JwtService jwtService;
    @Autowired private AdminDetailsService adminDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
		String authHeader = request.getHeader("Authorization"); 
		String token = null; 
		String username = null; 
		if (authHeader != null && authHeader.startsWith("Bearer ")) { 
			token = authHeader.substring(7); 
			username = jwtService.extractUsername(token); 
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
			UserDetails userDetails = adminDetailsService.loadUserByUsername(username); 

			System.out.println("\n--------Jwt Auth Filter 2----------\n");
			System.out.println("Username: " + userDetails.getUsername());
			System.out.println("Authorities: " + userDetails.getAuthorities());
			System.out.println("Account Non Expired: " + userDetails.isAccountNonExpired());
			System.out.println("Account Non Locked: " + userDetails.isAccountNonLocked());
			System.out.println("Credentials Non Expired: " + userDetails.isCredentialsNonExpired());
			System.out.println("Enabled: " + userDetails.isEnabled());
			System.out.println("\n------------------------------\n");

			if (jwtService.validateToken(token, userDetails)) { 
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
				SecurityContextHolder.getContext().setAuthentication(authToken); 
				System.out.println("\n--------Jwt Auth Filter 3----------\n");
				System.out.println(request.getHeader("Authorization"));
				System.out.println("\n------------------------------\n");
			} 
		} 

		filterChain.doFilter(request, response); 
	} 

	@PostConstruct
    public void init() {
        // This method will be called after the bean has been initialized
		System.out.println("\n------------------------------\n");
        System.out.println("JwtAuthFilter bean has been initialized.");
        System.out.println("JwtService: " + jwtService);
        System.out.println("AdminDetailsService: " + adminDetailsService);
		System.out.println("\n------------------------------\n");
    }
} 

