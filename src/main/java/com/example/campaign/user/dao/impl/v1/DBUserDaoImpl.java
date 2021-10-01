package com.example.campaign.user.dao.impl.v1;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.campaign.registration.svc.api.v1.dto.UserSvcBean;
import com.example.campaign.user.dao.api.v1.DBUserDao;
import com.example.campaign.user.dao.api.v1.dto.UserDaoBean;
import com.example.campaign.user.mapper.v1.UserMapper;

public class DBUserDaoImpl implements DBUserDao {

	@Autowired
	private UserMapper mapper;

	@Override
	public UserSvcBean getUser(String userId) {
		// log..
		UserDaoBean dbBean = mapper.getUser(userId);
		// log..
		UserSvcBean result = convertToApiBean(dbBean);
		// log..
		return result;
	}

	private UserSvcBean convertToApiBean(UserDaoBean in) {
		// nullcheck..
		UserSvcBean result = new UserSvcBean();
		result.setUserId(in.getUserId());
		result.setAddress(in.getAddress());
		result.setName(in.getName());
		result.setTerritory(in.getTerritory());
		return result;
	}

	@Override
	public int insertUser(UserSvcBean user) {
		// log..
		UserDaoBean toInsert = convertToUserDaoBean(user);
		// log..
		int res = mapper.insertUser(toInsert);
		// log..
		return res;
	}

	private UserDaoBean convertToUserDaoBean(UserSvcBean in) {
		// nullcheck..
		UserDaoBean result = new UserDaoBean();
		result.setUserId(in.getUserId());
		result.setAddress(in.getAddress());
		result.setName(in.getName());
		result.setTerritory(in.getTerritory());
		return result;
	}

}
