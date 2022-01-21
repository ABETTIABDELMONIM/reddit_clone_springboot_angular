package com.example.demo.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationEmail {
    private String subject;
    private String recipient;
    private String body;
    
}
