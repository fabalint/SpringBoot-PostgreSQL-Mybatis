package com.example.campaign.registration.controller.v1;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.campaign.registration.controller.v1.converter.RegistrationControllerUserConverter;
import com.example.campaign.registration.controller.v1.dto.User;
import com.example.campaign.registration.svc.api.v1.RegistrationService;
import com.example.campaign.registration.svc.api.v1.dto.UserSvcBean;

@RestController
@RequestMapping("/registration")
@Validated
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private RegistrationControllerUserConverter controllerUserConverter;

	@RequestMapping(method = RequestMethod.GET, path = "/v1/select")
	public ResponseEntity<User> select(//
			// better email validator.. or, IRL, userid validator.
			@RequestParam(name = "userId", required = true) @Size(min = 7, max = 128) String userId//
	) {
		// log
		UserSvcBean user = registrationService.getUser(userId);
		// log
		User res = controllerUserConverter.toControllerUserBean(user);
		// log
		return new ResponseEntity<User>(res, HttpStatus.ACCEPTED);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/v1/register")
	public ResponseEntity<String> register(//
			@Valid @RequestBody(required = true) User user//
	) {
		// log
		UserSvcBean apiUser = controllerUserConverter
				.toServiceUserBean(user);
		// log
		UserSvcBean storedUser = registrationService
				.insertUser(apiUser);
		// log
		return new ResponseEntity<String>(storedUser.getUserId(), HttpStatus.OK);
	}

	// ... update... delete...
}
