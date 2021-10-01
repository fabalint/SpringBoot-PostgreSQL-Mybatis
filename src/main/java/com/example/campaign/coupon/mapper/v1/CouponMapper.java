package com.example.campaign.coupon.mapper.v1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.campaign.coupon.dao.api.v1.dto.CouponRedeemResultDaoBean;

@Mapper
public interface CouponMapper {
	public CouponRedeemResultDaoBean redeemCoupon(@Param("userId") String userId, @Param("territory") String territory,
			@Param("couponCode") String couponCode);

}
