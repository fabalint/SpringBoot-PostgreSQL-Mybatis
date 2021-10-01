package com.example.campaign.lottery.svc.api.v1.copy;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public interface LotteryService {

	LotteryResultBean doVote(@Size(min = 7, max = 128) String userId, @Size(min = 2, max = 10) String territory);

	void addToWinnerSummary(@Size(min = 7, max = 128) String userId, @Size(min = 2, max = 10) String territory,
			@Pattern(regexp = "[0-9A-Z]{10}") String couponCode);

}
