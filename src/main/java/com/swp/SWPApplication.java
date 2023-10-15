package com.swp;

import com.swp.cms.controllers.PostApprovalsController;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SWPApplication {

	public static void main(String[] args) {
SpringApplication.run(SWPApplication.class, args);
//		ConfigurableApplicationContext context = SpringApplication.run(SWPApplication.class, args);
//
//		// Get the PostApprovalsController bean from the Spring context
//		PostApprovalsController postApprovalsController = context.getBean(PostApprovalsController.class);
//
//		// Call the getByIDD method to see the result
//		postApprovalsController.getByIDD();
//
//		// Close the Spring application context
//		context.close();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
