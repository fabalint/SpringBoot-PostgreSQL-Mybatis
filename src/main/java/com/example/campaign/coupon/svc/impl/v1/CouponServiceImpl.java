package com.example.campaign.coupon.svc.impl.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.campaign.coupon.dao.api.v1.DBCouponDao;
import com.example.campaign.coupon.svc.api.v1.CouponRedeemResult;
import com.example.campaign.coupon.svc.api.v1.CouponService;
import com.example.campaign.util.exception.v1.BusinessCodedException;

// TODO: exceeption-resolver..
public class CouponServiceImpl implements CouponService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CouponServiceImpl.class);

	@Autowired
	private DBCouponDao dbCouponDao;

	@Override
	public CouponRedeemResult redeemCoupon(String userId, String territory, String couponCode) {
		try {
			CouponRedeemResult result = dbCouponDao.redeemCoupon(userId, territory, couponCode);
			// ...
			return result;
		} catch (Exception e) {
			LOGGER.error("Redeem-failed", e);
			throw new BusinessCodedException(CouponService.COUPON_REDEEM_FAILED, e);
		}
	}

}
