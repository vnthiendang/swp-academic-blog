package com.swp.config;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.PostTagDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.util.List;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Add your custom mapping configurations here
        Type listMediaType = new TypeToken<List<MediaDto>>() {}.getType();
        modelMapper.addConverter(ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), listMediaType));

        Type listPostTagType = new TypeToken<List<PostTagDto>>() {}.getType();
        modelMapper.addConverter(ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), listPostTagType));

        return modelMapper;
    }
}