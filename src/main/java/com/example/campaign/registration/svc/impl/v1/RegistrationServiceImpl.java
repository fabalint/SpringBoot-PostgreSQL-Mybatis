package com.example.campaign.registration.svc.impl.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.campaign.registration.svc.api.v1.RegistrationService;
import com.example.campaign.registration.svc.api.v1.dto.UserSvcBean;
import com.example.campaign.user.dao.api.v1.DBUserDao;
import com.example.campaign.util.exception.v1.BusinessCodedException;

// TODO: exceeption-resolver..
public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);
	
	@Autowired
	private DBUserDao dbUserDao;

	@Override
	public UserSvcBean getUser(/*valid emali..*/String userId) {
		try {
			UserSvcBean resultUser = dbUserDao.getUser(userId);
			// log..
			return resultUser;
		} catch (Exception e) {
			LOGGER.error("getUSer: cannot fetch:", e);
			throw new BusinessCodedException(RegistrationService.REGISTER_USER_FAILED_TO_FETCH);
		}
	}

	@Override
	public UserSvcBean insertUser(UserSvcBean user) {
		int mapperRes;
		try {
			mapperRes = dbUserDao.insertUser(user);
			// log..
		} catch (Exception e) {
			// log..
			throw new BusinessCodedException(RegistrationService.REGISTER_USER_FAIL_TO_ADD, e);
		}
		if (mapperRes > 0) {
			try {
				UserSvcBean resultUser = dbUserDao.getUser(user.getUserId());
				// log..
				return resultUser;
			} catch (Exception e) {
				// log..
				throw new BusinessCodedException(RegistrationService.REGISTER_USER_FAIL_TO_FETCH, e);
			}
		} else {
			// TODO: logging
			throw new BusinessCodedException(RegistrationService.REGISTER_USER_FAIL_TO_FETCH);
		}
	}

}
