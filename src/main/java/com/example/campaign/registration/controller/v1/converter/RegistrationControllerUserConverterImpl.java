package com.example.campaign.registration.controller.v1.converter;

import java.util.ArrayList;
import java.util.List;

import com.example.campaign.registration.controller.v1.dto.User;
import com.example.campaign.registration.svc.api.v1.dto.UserSvcBean;

public class RegistrationControllerUserConverterImpl implements RegistrationControllerUserConverter {

	// or an automatic bean mapper..
	// but: this is a possibility to validate business rules..
	
	public User toControllerUserBean(UserSvcBean in) {
		User result = new User();

		result.setUserId(in.getUserId());
		result.setAddress(in.getAddress());
		result.setName(in.getName());
		result.setTerritory(in.getTerritory());

		return result;
	}

	public UserSvcBean toServiceUserBean(User in) {
		UserSvcBean result = new UserSvcBean();

		result.setUserId(in.getUserId());
		result.setAddress(in.getAddress());
		result.setName(in.getName());
		result.setTerritory(in.getTerritory());

		return result;
	}

	@Override
	public List<User> toControllerUserBean(List<UserSvcBean> users) {
		List<User> res = new ArrayList<User>();
		for (UserSvcBean user : users) {
			res.add(toControllerUserBean(user));
		}
		return res;
	}
}
