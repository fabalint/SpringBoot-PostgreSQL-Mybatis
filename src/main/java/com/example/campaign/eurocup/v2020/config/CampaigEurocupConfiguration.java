package com.example.campaign.eurocup.v2020.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.campaign.coupon.controller.v1.config.CouponControllerConfiguration;
import com.example.campaign.coupon.dao.impl.v1.config.DBCouponDaoConfig;
import com.example.campaign.coupon.mapper.v1.CouponMapperConfiguration;
import com.example.campaign.coupon.svc.impl.v1.config.CouponServiceConfiguration;
import com.example.campaign.filter.errorhandler.v1.config.ErrorHandlerConfig;
import com.example.campaign.lottery.svc.impl.v1.config.LotteryServiceConfiguration;
import com.example.campaign.registration.controller.v1.config.RegistrationControllerConfig;
import com.example.campaign.registration.svc.impl.v1.config.RegistrationServiceConfiguration;
import com.example.campaign.summary.controller.v1.config.SummaryConfig;
import com.example.campaign.user.dao.impl.v1.config.DBUserDaoConfig;
import com.example.campaign.user.mapper.v1.UserMapperConfiguration;

@Configuration
@Import({ //
		// todo: group related configurations together, to make it easier to use.. testcontext, 
		CouponControllerConfiguration.class, //
		CouponServiceConfiguration.class,//
		DBCouponDaoConfig.class,//
		CouponMapperConfiguration.class,//
		RegistrationControllerConfig.class, //
		SummaryConfig.class, //
		RegistrationServiceConfiguration.class, //
		LotteryServiceConfiguration.class,//
		DBUserDaoConfig.class, //
		UserMapperConfiguration.class, //
		
		ErrorHandlerConfig.class, //
		FlywayConfig.class,//
})
public class CampaigEurocupConfiguration {

}
