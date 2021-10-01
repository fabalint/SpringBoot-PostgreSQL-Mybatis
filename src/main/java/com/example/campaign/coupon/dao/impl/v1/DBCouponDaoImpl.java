package com.example.campaign.coupon.dao.impl.v1;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.campaign.coupon.dao.api.v1.DBCouponDao;
import com.example.campaign.coupon.dao.api.v1.dto.CouponRedeemResultDaoBean;
import com.example.campaign.coupon.mapper.v1.CouponMapper;
import com.example.campaign.coupon.svc.api.v1.CouponRedeemResult;

public class DBCouponDaoImpl implements DBCouponDao {

	@Autowired
	private CouponMapper mapper;

	@Override
	public CouponRedeemResult redeemCoupon(String userId, String territory, String couponCode) {
		// log..
		CouponRedeemResultDaoBean dbBean = mapper.redeemCoupon(userId, territory, couponCode);
		// log..
		CouponRedeemResult result = convertToApiBean(dbBean);
		// log..
		return result;
	}

	private CouponRedeemResult convertToApiBean(CouponRedeemResultDaoBean in) {
		// nullcheck..
		CouponRedeemResult result = new CouponRedeemResult();
		result.setResultCode(in.getResultCode());
		result.setRetryable(in.getRetryable());
		result.setSuccess(in.getSuccess());
		// maybe later coupon+territory too, so its clients can see which request
		// failed..
		return result;
	}

}
