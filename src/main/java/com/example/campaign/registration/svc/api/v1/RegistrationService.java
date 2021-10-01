package com.example.campaign.registration.svc.api.v1;

import com.example.campaign.registration.svc.api.v1.dto.UserSvcBean;

public interface RegistrationService {

	public static final String REGISTER_USER_FAILED_TO_FETCH = "REGISTER_USER_FAILED_TO_FETCH";
	public static final String REGISTER_USER_FAIL_TO_ADD = "REGISTER_USER_FAIL_TO_ADD";
	public static final String REGISTER_USER_FAIL_TO_FETCH = "REGISTER_USER_FAIL_TO_FETCH";

	public UserSvcBean getUser(String userId);

	public UserSvcBean insertUser(UserSvcBean user);

}
