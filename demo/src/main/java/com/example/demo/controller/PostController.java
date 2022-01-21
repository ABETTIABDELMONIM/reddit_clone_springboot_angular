package com.example.demo.controller;

import java.util.List;

import com.example.demo.service.PostService;

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
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;
    
    @GetMapping
    ResponseEntity<List<PostDto>> getAllPost(){
        return ResponseEntity.status(HttpStatus.OK)
                                .body(this.postService.getAll());
    }

    @PostMapping
    ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(postService.createPost(postDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                                .body(postService.getPostById(id));
    }

    
}
