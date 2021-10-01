package com.example.campaign.user.dao.api.v1;

import com.example.campaign.registration.svc.api.v1.dto.UserSvcBean;

public interface DBUserDao {

	public UserSvcBean getUser(String userId);

	public int insertUser(UserSvcBean user);

}
