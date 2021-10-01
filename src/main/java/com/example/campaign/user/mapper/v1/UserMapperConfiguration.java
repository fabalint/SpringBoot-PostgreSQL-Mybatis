package com.example.campaign.user.mapper.v1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.example.campaign.user.mapper.v1")
public class UserMapperConfiguration {

}
