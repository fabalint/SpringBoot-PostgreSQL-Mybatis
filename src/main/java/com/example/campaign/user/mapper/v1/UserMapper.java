package com.example.campaign.user.mapper.v1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.campaign.user.dao.api.v1.dto.UserDaoBean;

@Mapper
public interface UserMapper {

	public UserDaoBean getUser(@Param("userId") String userId);

	public int insertUser(@Param("user") UserDaoBean user);

}
