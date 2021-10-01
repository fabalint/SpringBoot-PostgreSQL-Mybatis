package com.example.campaign.user.dao.impl.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.campaign.user.dao.api.v1.DBUserDao;
import com.example.campaign.user.dao.impl.v1.DBUserDaoImpl;

@Configuration
public class DBUserDaoConfig {

	@Bean("DBUserDaoImpl_v1")
	public DBUserDao getDBUserDao() {
		return new DBUserDaoImpl();
	}
}
