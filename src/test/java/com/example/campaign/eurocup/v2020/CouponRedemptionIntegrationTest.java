package com.example.campaign.eurocup.v2020;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.constraints.Pattern;

import org.flywaydb.core.internal.util.StringUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.campaign.coupon.controller.v1.CouponController;
import com.example.campaign.coupon.svc.api.v1.CouponRedeemResult;

@SpringBootTest
class CouponRedemptionIntegrationTest {

	// to help with the test-writing - should be off when running for performance.
	// Or on the minimar required level.
	private static final Logger LOGGER = LoggerFactory.getLogger(CouponRedemptionIntegrationTest.class);

	@Autowired
	CouponController controller;

	private AtomicInteger couponCodeCounter = new AtomicInteger(new Random().nextInt(100000));

	@RepeatedTest(value = 2000, name = "Thread {currentRepetition}/{totalRepetitions}")
	void contextLoads() {
		String couponCode = getCouponCode();
		ResponseEntity<CouponRedeemResult> res = controller.redeem(couponCode, "de.0@de.de", "de");
		LOGGER.debug("Redeemed {} as {}", couponCode, res);
		assertEquals(HttpStatus.OK.value(), res.getStatusCode().value());
	}

	private @Pattern(regexp = "[0-9A-Z]{10}") String getCouponCode() {
		int code = couponCodeCounter.getAndAdd(1);
		String coupon = StringUtils.leftPad(String.valueOf(code), 10, '0');
		return coupon;
	}

}
