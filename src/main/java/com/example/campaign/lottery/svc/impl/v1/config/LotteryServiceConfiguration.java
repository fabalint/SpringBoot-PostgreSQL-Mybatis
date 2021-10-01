package com.example.campaign.lottery.svc.impl.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.campaign.lottery.svc.api.v1.copy.LotteryService;
import com.example.campaign.lottery.svc.impl.v1.LotteryServiceImpl;

@Configuration
public class LotteryServiceConfiguration {

	@Bean("LotteryService_v1")
	public LotteryService getLotteryService() {
		return new LotteryServiceImpl();
	}
}
