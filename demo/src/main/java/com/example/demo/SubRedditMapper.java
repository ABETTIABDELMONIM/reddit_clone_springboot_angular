package com.example.demo;

import java.util.List;

import com.example.demo.controller.SubRedditDto;
import com.example.demo.model.Post;
import com.example.demo.model.SubReddit;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubRedditMapper {

    @Mapping(target = "numberOfPost", expression = "java(mapPosts(subReddit.getPosts()))")
    SubRedditDto mapSubRedditToDto(SubReddit subReddit);

    default String mapPosts(List<Post> posts){return String.valueOf(posts == null ? 0 : posts.size());}

    @InheritInverseConfiguration
    @Mapping( target = "posts", ignore = true)
    SubReddit mapDtoToSubReddit(SubRedditDto subRedditDto);
    
}
