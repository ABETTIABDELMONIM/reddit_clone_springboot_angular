package com.example.demo.config;

import com.example.demo.security.JwtAuthentificationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final JwtAuthentificationFilter jwtAuthentificationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable()
       .authorizeRequests()
       .antMatchers("/**")
       .permitAll()
       .antMatchers("/h2-console/**")
       .permitAll()
       .anyRequest()
       .authenticated();
       http.headers().frameOptions().disable();
       http.addFilterBefore(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);
    }



    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManager();

    }
    
}
