package com.example.campaign.registration.controller.v1.converter;

import java.util.List;

import com.example.campaign.registration.controller.v1.dto.User;
import com.example.campaign.registration.svc.api.v1.dto.UserSvcBean;

public interface RegistrationControllerUserConverter {

	User toControllerUserBean(UserSvcBean svcUser);
	
	UserSvcBean toServiceUserBean(User ctrlUser);

	List<User> toControllerUserBean(
			List<UserSvcBean> users);
}
