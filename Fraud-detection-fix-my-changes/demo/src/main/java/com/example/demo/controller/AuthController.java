package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        return authService.signup(user);
    }

    // @PostMapping("/login")
    // public String login(@RequestBody LoginRequest request) {
    //     return authService.login(request.getUsername(), request.getPassword());
    // }
  @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Map<String, Object> response = authService.login(username, password);
        return ResponseEntity.ok(response);
    }
}

class LoginRequest {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
