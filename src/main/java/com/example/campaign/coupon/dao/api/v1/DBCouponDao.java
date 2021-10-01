package com.example.campaign.coupon.dao.api.v1;

import com.example.campaign.coupon.svc.api.v1.CouponRedeemResult;

public interface DBCouponDao {

	CouponRedeemResult redeemCoupon(String userId, String territory, String couponCode);

}
