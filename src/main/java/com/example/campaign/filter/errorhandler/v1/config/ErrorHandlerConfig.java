package com.example.campaign.filter.errorhandler.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.campaign.filter.errorhandler.v1.ErrorController;

@Configuration
public class ErrorHandlerConfig {

	@Bean("ErrorController_v1")
	public ErrorController getErrorController() {
		return new ErrorController();
	}

	
}
