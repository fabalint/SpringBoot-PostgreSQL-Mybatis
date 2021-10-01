package com.example.campaign.coupon.controller.v1;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.campaign.coupon.svc.api.v1.CouponRedeemResult;
import com.example.campaign.coupon.svc.api.v1.CouponService;
import com.example.campaign.lottery.svc.api.v1.copy.LotteryResultBean;
import com.example.campaign.lottery.svc.api.v1.copy.LotteryService;

@RestController
@RequestMapping("/coupon")
@Validated
public class CouponController {

	@Autowired
	private CouponService couponService;

	@Autowired
	private LotteryService lotteryService;

	@RequestMapping(method = RequestMethod.GET, path = "/v1/redeem")
	// todo generated/controller-layer bean..
	// maybe a composite, which can hold a redeem result, and one or more lottery
	// results,
	// or two lists for amulti-coupon-redemption-song..
	//
	public ResponseEntity<CouponRedeemResult> redeem(//
			@RequestParam(name = "couponCode", required = true) @Pattern(regexp = "[0-9A-Z]{10}") String couponCode, //
			@RequestParam(name = "userId", required = true) @Size(min = 7, max = 128) String userId, //
			@RequestParam(name = "territory", required = true) @Size(min = 2, max = 10) String territory//
	) {
		// log
		CouponRedeemResult couponRedeemResult = couponService.redeemCoupon(userId, territory, couponCode);
		if (Boolean.TRUE.equals(couponRedeemResult.getSuccess())) {
			// log
			LotteryResultBean lotteryResult = lotteryService.doVote(userId, territory);

			// log
			CouponRedeemResult result = dealWithLotteryResult(lotteryResult, couponRedeemResult, userId, territory,
					couponCode);
			// log
			return new ResponseEntity<CouponRedeemResult>(result, HttpStatus.OK);
		} else {
			// log
			return new ResponseEntity<CouponRedeemResult>(couponRedeemResult, HttpStatus.CONFLICT);
		}
	}

	private CouponRedeemResult dealWithLotteryResult(LotteryResultBean lotteryResult,
			CouponRedeemResult resultOfRedemption, @Size(min = 7, max = 128) String userId,
			@Size(min = 2, max = 10) String territory, @Pattern(regexp = "[0-9A-Z]{10}") String couponCode) {
		CouponRedeemResult res = new CouponRedeemResult();
		res.setRetryable(false);
		res.setSuccess(true);

		if (lotteryResult == null) {
			// LOG: someone had problems with woting.. maybe enqueue for later?
			res.setResultCode(resultOfRedemption.getResultCode());
			return res;
		}
		if (LotteryResultBean.LOTTERY_RESULT_WON.equals(lotteryResult.getResultCode())) {
			// own codes for the api of the controller.. and enums..
			// log..
			res.setResultCode(LotteryResultBean.LOTTERY_RESULT_WON);

			lotteryService.addToWinnerSummary(userId, territory,
					couponCode/* , AND THE DATE RECEIVED FROM THE REDEEM_COUPON to be in sync.. */);

		} else {
			// own codes for the api of the controller.. and enums..
			// log..
			res.setResultCode(LotteryResultBean.LOTTERY_RESULT_LATER);
		}

		return res;
	}
}
