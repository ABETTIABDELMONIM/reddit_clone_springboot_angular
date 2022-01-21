package com.example.demo.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String postName;

    @Nullable
    private String url;
    @Nullable
    @Lob
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private User  user;
    private Instant createdTime;
    @ManyToOne(fetch = FetchType.LAZY)
    private SubReddit SubReddit;
    
}
