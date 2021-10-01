package com.example.campaign.lottery.svc.api.v1.copy;

public class LotteryResultBean {

	// enum...
	public static final String LOTTERY_RESULT_WON = "LOTTERY_RESULT_WON";
	public static final String LOTTERY_RESULT_LATER = "LOTTERY_RESULT_LATER";

	private String resultCode;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

}
