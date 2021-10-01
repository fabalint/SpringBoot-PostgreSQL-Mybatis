package com.example.campaign.registration.controller.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.campaign.registration.controller.v1.RegistrationController;
import com.example.campaign.registration.controller.v1.converter.RegistrationControllerUserConverter;
import com.example.campaign.registration.controller.v1.converter.RegistrationControllerUserConverterImpl;

@Configuration
public class RegistrationControllerConfig {

	@Bean("RegistrationController_v1")
	public RegistrationController getRegistrationController() {
		return new RegistrationController();
	}

	@Bean("ControllerUserConverter_v1")
	public RegistrationControllerUserConverter getControllerUserConverter() {
		return new RegistrationControllerUserConverterImpl();
	}

}
