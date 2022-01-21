package com.example.demo.service;

import static java.util.Collections.singletonList;

import java.util.Collection;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
        .orElseThrow(()-> new RuntimeException("User not exist with userName : "+ userName));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),user.isEnabled(),
        true,true,true, getAuthorities("USER"));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
    
}
