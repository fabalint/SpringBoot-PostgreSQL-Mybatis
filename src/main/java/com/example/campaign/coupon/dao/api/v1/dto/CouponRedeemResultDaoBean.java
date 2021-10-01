package com.example.campaign.coupon.dao.api.v1.dto;

public class CouponRedeemResultDaoBean {

	private String resultCode;
	private Boolean success;
	private Boolean retryable;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Boolean getRetryable() {
		return retryable;
	}

	public void setRetryable(Boolean retryable) {
		this.retryable = retryable;
	}
}
