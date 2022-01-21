package com.example.demo.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.controller.PostDto;
import com.example.demo.model.Post;
import com.example.demo.model.SubReddit;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.SubRedditRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubRedditRepository subRedditRepository;

    public List<PostDto> getAll(){
       return  this.postRepository.findAll()
                            .stream()
                            .map( post -> PostDto.builder()
                            .description(post.getDescription())
                            .postName(post.getPostName())
                            .duration(String.valueOf(post.getCreatedTime().toEpochMilli()))
                            .subredditName(post.getSubReddit().getName())
                            .id(post.getId().toString())
                            .userName(post.getUser().getUserName())
                            .build())
                            .collect(Collectors.toList());

    }

    public PostDto createPost(PostDto postDto) {

        //get user by username
        User user = userRepository.findByUserName(postDto.getUserName())
                                    .orElseThrow( ()->new RuntimeException(String.format("Cannot create post with username : %s"
                                                                            ,postDto.getUserName())));
        //get subredit by name
        SubReddit subReddit = subRedditRepository.findByName(postDto.getSubredditName())
                                                 .orElseThrow( () ->new RuntimeException(String.format("Cannot create post with subreddit name : %s"
                                                                                        ,postDto.getSubredditName())));
        //create post model

        Post post = Post.builder().postName(postDto.getPostName())
                                 .description(postDto.getDescription())
                                 .url(postDto.getUrl())
                                 .user(user)
                                 .SubReddit(subReddit)
                                 .createdTime(Instant.now())
                                 .build();
        //save
        
        Post savedPost = postRepository.save(post);
        return PostDto.builder()
                        .description(savedPost.getDescription())
                        .postName(savedPost.getPostName())
                        .duration(String.valueOf(savedPost.getCreatedTime().toEpochMilli()))
                        .subredditName(savedPost.getSubReddit().getName())
                        .id(savedPost.getId().toString())
                        .userName(savedPost.getUser().getUserName())
                        .build();
    }

    public PostDto getPostById(Long id) {
        Post savedPost = postRepository.findById(id)
                                        .orElseThrow( ()->new RuntimeException(String.format("Cannot find post with id : %d"
                                        ,id)));
        return PostDto.builder()
                        .description(savedPost.getDescription())
                        .postName(savedPost.getPostName())
                        .duration(String.valueOf(savedPost.getCreatedTime().toEpochMilli()))
                        .subredditName(savedPost.getSubReddit().getName())
                        .id(savedPost.getId().toString())
                        .userName(savedPost.getUser().getUserName())
                        .build();
    }
}
