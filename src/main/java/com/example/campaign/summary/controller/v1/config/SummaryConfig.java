package com.example.campaign.summary.controller.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.campaign.summary.controller.v1.SummaryController;

@Configuration
public class SummaryConfig {


	@Bean(name = "SummaryController_v1")
	public SummaryController getSummaryController() {
		return new SummaryController();
	}
}
