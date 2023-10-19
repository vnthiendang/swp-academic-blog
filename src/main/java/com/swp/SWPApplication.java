package com.swp;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.PostTagDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Type;
import java.util.List;

@SpringBootApplication
public class SWPApplication {

	public static void main(String[] args) {
		SpringApplication.run(SWPApplication.class, args);
	}

//	@Bean
//	public ModelMapper modelMapper() {
//		ModelMapper modelMapper = new ModelMapper();
//
//		// Add your custom mapping configurations here
//		Type listMediaType = new TypeToken<List<MediaDto>>() {}.getType();
//		modelMapper.addConverter(ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), listMediaType));
//
//		Type listPostTagType = new TypeToken<List<PostTagDto>>() {}.getType();
//		modelMapper.addConverter(ctx -> ctx.getSource() == null ? null : modelMapper.map(ctx.getSource(), listPostTagType));
//
//		return modelMapper;
//	}
}
