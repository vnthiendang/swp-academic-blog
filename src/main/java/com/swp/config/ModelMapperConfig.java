package com.swp.config;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.PostDto;
import com.swp.entities.Media;
import com.swp.entities.Post;
import com.swp.entities.PostTag;
import com.swp.entities.Tag;
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

        return modelMapper;
    }
}

