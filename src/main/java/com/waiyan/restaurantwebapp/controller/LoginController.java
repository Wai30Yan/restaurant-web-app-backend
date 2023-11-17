package com.waiyan.restaurantwebapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waiyan.restaurantwebapp.model.AdminUserInfo;
import com.waiyan.restaurantwebapp.services.auth.AdminDetailsService;
import com.waiyan.restaurantwebapp.services.auth.JwtService;

import jakarta.annotation.PostConstruct;


@RestController
@CrossOrigin(origins = "https://restaurant-web-app-frontend.vercel.app")
// @CrossOrigin(origins = "http://localhost:3000")
// @CrossOrigin(origins = "https://restaurant-web-app-frontend-jls78ufaz-wai30yan.vercel.app/")
@RequestMapping("/admin")
public class LoginController {

    @Autowired private JwtService jwtService; 
    @Autowired private AuthenticationManager authenticationManager;
    
    @Autowired private AdminDetailsService adminDetailsService;

    @PostConstruct
    public void init() {
        // This method will be called after the bean has been initialized
        System.out.println("\n------------------------------\n");
        System.out.println("LoginController bean has been initialized.");
        System.out.println("JwtService: " + jwtService);
        System.out.println("AuthenticationManager: " + authenticationManager);
        System.out.println("AdminDetailsService: " + adminDetailsService);
        System.out.println("\n------------------------------\n");
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody Map<String, String> loginForm) {
        String username = loginForm.get("username");
        String password = loginForm.get("password");

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
        System.out.println("\n------------Login Request-------------\n");
        System.out.println(username + " " + password);
        // System.out.println(authenticationManager.authenticate(authReq));
        System.out.println("\n-------------------------\n");
        
        try {
            Authentication authResponse = authenticationManager.authenticate(authReq);
            System.out.println("\n------------Auth Response-------------\n");
            System.out.println(authResponse);

            if (authResponse.isAuthenticated()) {
                String jwtToken = jwtService.generateToken(username);
                System.out.println("\n------------Login Controller-------------\n");
                SecurityContextHolder.getContext().setAuthentication(authResponse);
                System.out.println(SecurityContextHolder.getContext().getAuthentication());


                System.out.println("\n-------------------------\n");
                
                return ResponseEntity.ok(jwtToken);
            }
        } catch (AuthenticationException e) {
            System.out.println("From LoginController " + e.getMessage());
            return ResponseEntity.badRequest().body("Wrong Credentials.");
        }
        return ResponseEntity.badRequest().body("Wrong Credentials.");
    }

    @PostMapping("/logout")
    public void logoutAdmin() {
        
    }

    @PostMapping("/create-admin")
    public void createAdmin(@RequestBody Map<String, String> newAdmin) {
        String username = newAdmin.get("username");
        String password = newAdmin.get("password");
        AdminUserInfo userInfo = new AdminUserInfo();
        userInfo.setUserName(username);
        userInfo.setPassword(password);
        adminDetailsService.addUser(userInfo);
    }
}
