package com.example.campaign.coupon.dao.impl.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.campaign.coupon.dao.api.v1.DBCouponDao;
import com.example.campaign.coupon.dao.impl.v1.DBCouponDaoImpl;

@Configuration
public class DBCouponDaoConfig {

	@Bean("DBCouponDaoImpl_v1")
	public DBCouponDao getDBCouponDao() {
		return new DBCouponDaoImpl();
	}
}
