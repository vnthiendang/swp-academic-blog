package com.swp.config;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.PostDto;
import com.swp.cms.dto.ReportDto;
import com.swp.entities.*;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(context -> context.getSource() != null);


        // Define the mapping for the 'tags' property using a TypeMap
        TypeMap<Post, PostDto> typeMap = modelMapper.createTypeMap(Post.class, PostDto.class);
        typeMap.addMapping(src -> src.getMedias(), PostDto::setMediaList);
        typeMap.addMapping(src -> src.getAwards(), PostDto::setAwardList);
        typeMap.addMapping(src -> src.getComments(), PostDto::setCommentList);
        typeMap.addMapping(src -> src.getVotes(), PostDto::setVoteList);
        typeMap.addMapping(src -> src.getTags(), PostDto::setTagList);

        // Define the mapping for Media to MediaDto
        TypeMap<Media, MediaDto> mediaTypeMap = modelMapper.createTypeMap(Media.class, MediaDto.class);
        mediaTypeMap.addMapping(Media::getData, MediaDto::setData);

        // Define the mapping for Report to ReportDto
        TypeMap<Report, ReportDto> reportTypeMap = modelMapper.createTypeMap(Report.class, ReportDto.class);
        reportTypeMap.addMapping(src -> src.getViolationRules(), ReportDto::setViolationRuleList);
        return modelMapper;
    }
}

