package com.example.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

  private String id;
  private String postName;
  private String url;
  private String description;
  private String voteCount;
  private String subredditName;
  private String commentCount;
  private String duration;
  private String userName;
    
}
