package com.example.demo.controller;

import com.example.demo.service.SubRedditService;

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
@RequestMapping("/api/reddit")
public class SubRedditController {

    private final SubRedditService subRedditService;
    @PostMapping
    public ResponseEntity<?> createSubReddit(@RequestBody SubRedditDto subredditDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(subRedditService.save(subredditDto));
    }
    
    @GetMapping
    public ResponseEntity<?> getSubReddits(){
       return ResponseEntity.status(HttpStatus.OK)
                            .body(subRedditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubReddit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                                .body(subRedditService.getSubRedditById(id));
    }
}
