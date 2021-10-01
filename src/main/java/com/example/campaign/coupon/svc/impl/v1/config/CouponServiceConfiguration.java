package com.example.campaign.coupon.svc.impl.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.campaign.coupon.svc.api.v1.CouponService;
import com.example.campaign.coupon.svc.impl.v1.CouponServiceImpl;

@Configuration
public class CouponServiceConfiguration {

	@Bean("CouponService_v1")
	public CouponService getCouponService() {
		return new CouponServiceImpl();
	}
}
