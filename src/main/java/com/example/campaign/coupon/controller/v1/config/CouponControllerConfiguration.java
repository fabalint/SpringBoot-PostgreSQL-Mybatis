package com.example.campaign.coupon.controller.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.campaign.coupon.controller.v1.CouponController;

@Configuration
public class CouponControllerConfiguration {

	@Bean(name = "CouponController_v1")
	public CouponController getCouponController() {
		return new CouponController();
	}
}
