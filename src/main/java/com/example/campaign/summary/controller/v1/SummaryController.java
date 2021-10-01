package com.example.campaign.summary.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.campaign.registration.controller.v1.dto.User;

@RestController
@RequestMapping("/summary")
@Validated
public class SummaryController {

	// params: dateFrom, dateTo, pageSize, showPageNo, [...]
	// TODO: mapper, dao, svc
	// to query : group_by 	CMPN_COUPON_WINNER_SUMMARY_V1.territory...
	// list of something beans..
	@RequestMapping(method = RequestMethod.GET, path = "/v1/results")
	public ResponseEntity<User> results() {
		// log
		// log
		User res = new User();
		// log
		return new ResponseEntity<User>(res, HttpStatus.ACCEPTED);
	}
}
