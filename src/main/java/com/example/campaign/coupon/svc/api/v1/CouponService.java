package com.example.campaign.coupon.svc.api.v1;

public interface CouponService {

	public static final String COUPON_REDEEM_FAILED = "COUPON_REDEEM_FAILED";

	CouponRedeemResult redeemCoupon(String userId, String territory, String couponCode);

}
