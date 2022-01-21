package com.example.demo.service;

import java.time.Instant;
import java.util.UUID;

import com.example.demo.controller.AuthenticationResponse;
import com.example.demo.controller.LoginRequest;
import com.example.demo.controller.RefreshTokemRequest;
import com.example.demo.controller.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.security.JwtProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
@AllArgsConstructor
@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private MailService mailService;
    private AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public void signUp(RegisterRequest registerRequest){
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);

        String token = generateVerificationToken(user);
        NotificationEmail notificationEmail = new NotificationEmail(
            "Please activate your account", 
            user.getEmail(),
            "Thank you for signing up to Reddit clone. Please click on the below url to activate your account: "+
            "http://localhost:8080/api/auth/acountVerification/"+token
            );
        mailService.sendEmail(notificationEmail);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                                                .orElseThrow(() -> new RuntimeException("Error : no token found !!!"));               
        User user = verificationToken.getUser() ;
        user.setEnabled(true);
        userRepository.save(user);
        log.info("Account activated succussfully !!!! ");
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
       Authentication authentication =  authenticationManager.authenticate(
           new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  AuthenticationResponse.builder()
                                        .token(jwtProvider.generateToken(authentication))
                                        .userName(loginRequest.getUserName())
                                        .refreshToken("")
                                        .expiredAt(Instant.now().plusMillis(jwtProvider.getJwtExpiredAtMillis()))
                                        .build();
         


    }

    public AuthenticationResponse refershToken(RefreshTokemRequest refreshTokemRequest) {
      return  AuthenticationResponse.builder()
                                         .token(jwtProvider.refreshToken(refreshTokemRequest.getUserName()))
                                         .userName(refreshTokemRequest.getUserName())
                                         .refreshToken("")
                                         .expiredAt(Instant.now().plusMillis(jwtProvider.getJwtExpiredAtMillis()))
                                         .build();
    }
    
}
