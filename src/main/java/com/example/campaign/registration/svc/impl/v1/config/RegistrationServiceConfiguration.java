package com.example.campaign.registration.svc.impl.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.campaign.registration.svc.api.v1.RegistrationService;
import com.example.campaign.registration.svc.impl.v1.RegistrationServiceImpl;

@Configuration
public class RegistrationServiceConfiguration {

	@Bean("RegistrationService_v1")
	public RegistrationService getRegistrationService() {
		return new RegistrationServiceImpl();
	}
}
