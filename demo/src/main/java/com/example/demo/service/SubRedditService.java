package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.SubRedditMapper;
import com.example.demo.controller.SubRedditDto;
import com.example.demo.model.SubReddit;
import com.example.demo.repository.SubRedditRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class SubRedditService {
    private final  SubRedditRepository subRedditRepository;
    private final SubRedditMapper subRedditMapper;

    public SubRedditDto save(SubRedditDto subRedditDto){

       SubReddit saveSubReddit =  subRedditRepository.save(subRedditMapper.mapDtoToSubReddit(subRedditDto));
      return  subRedditMapper.mapSubRedditToDto(saveSubReddit);
       
       
    }
    
    public List<SubRedditDto> getAll() {

       return subRedditRepository.findAll()
                            .stream()
                            .map(subRedditMapper::mapSubRedditToDto)
                            .collect(Collectors.toList());
    }

    public SubReddit getSubRedditById(Long id) {
        return subRedditRepository.findById(id)
                                .orElseThrow(()-> new RuntimeException("SubReddit does not exist with "+ id));
    }
    
    
    
}
