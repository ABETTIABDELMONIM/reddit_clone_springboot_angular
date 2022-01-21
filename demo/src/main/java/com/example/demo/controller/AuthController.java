package com.example.demo.controller;

import com.example.demo.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String>  signup(@RequestBody RegisterRequest registerRequest){
        authService.signUp(registerRequest);
        return new  ResponseEntity<String>("User registration successfull",HttpStatus.OK);
    }

    @GetMapping("acountVerification/{token}")
    public ResponseEntity<String> acountVerification(@PathVariable String token){
        authService.verifyAccount(token);
        return new  ResponseEntity<String>("Account activated successfully !!!",HttpStatus.OK);
    }

    @PostMapping("Login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
    @PostMapping("refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshTokemRequest refreshTokemRequest){
        return ResponseEntity.status(HttpStatus.OK)
                            .body(authService.refershToken(refreshTokemRequest));
    }
}
