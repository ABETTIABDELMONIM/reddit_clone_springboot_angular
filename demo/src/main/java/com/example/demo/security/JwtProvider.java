package com.example.demo.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class JwtProvider {
    @Value("${jwt.expiration.time}")
    private Long jwtExpiredInMillis;

    private KeyStore keyStore;
    @PostConstruct
    private void init(){
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream inputStream = getClass().getResourceAsStream("springblogg.jks");
            
                keyStore.load(inputStream, "secretsecret".toCharArray() );
            } catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
                log.error("Exception ", e);
            } 
    }

    public String generateToken(Authentication authentication){
       User user =  (User) authentication.getPrincipal();
       return Jwts.builder()
       .setSubject(user.getUsername())
       .setIssuedAt(Date.from(Instant.now()))
       .setExpiration(Date.from(Instant.now().plusMillis(jwtExpiredInMillis)))
       .signWith(SignatureAlgorithm.HS512, "HRlELXqpSBDFDSFDSFDSFJLJLJDSLFJLDSFJLDSFJLKDSJFLDSJFLDSJFLDJSLFJDSLFDSJFLJDSLFJDSLFJDSLFJLDSJFLDSJFLDSJFL")
       .compact();
    }

    public String refreshToken(String userName){
        return Jwts.builder()
        .setSubject(userName)
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plusMillis(jwtExpiredInMillis)))
        .signWith(SignatureAlgorithm.HS512, "HRlELXqpSBDFDSFDSFDSFJLJLJDSLFJLDSFJLDSFJLKDSJFLDSJFLDSJFLDJSLFJDSLFDSJFLJDSLFJDSLFJDSLFJLDSJFLDSJFLDSJFL")
        .compact();
     }

    // private Key getPrivateKey() {
    //    // String encodedString = Base64.getEncoder().encodeToString("secret".getBytes());
       
    //     try {
    //         return keyStore.getKey("springblog", "secretsecret".toCharArray());
    //     } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
    //         log.error("Exception ", e);
    //     }
    //     return null;
    // }
    public String getUserNameFromJwt(String jwt){
        Claims claims = Jwts.parser()
                            .setSigningKey("HRlELXqpSBDFDSFDSFDSFJLJLJDSLFJLDSFJLDSFJLKDSJFLDSJFLDSJFLDJSLFJDSLFDSJFLJDSLFJDSLFJDSLFJLDSJFLDSJFLDSJFL")
                            .parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }

    public long getJwtExpiredAtMillis() {
        return jwtExpiredInMillis;
    }
}
