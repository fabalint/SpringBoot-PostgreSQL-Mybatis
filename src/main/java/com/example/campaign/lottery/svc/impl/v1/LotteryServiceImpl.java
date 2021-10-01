package com.example.campaign.lottery.svc.impl.v1;

import java.util.Random;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.campaign.lottery.svc.api.v1.copy.LotteryResultBean;
import com.example.campaign.lottery.svc.api.v1.copy.LotteryService;

// TODO: exceeption-resolver..
public class LotteryServiceImpl implements LotteryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LotteryServiceImpl.class);

	// todo : to query the daily limits of different territories..
	//	@Autowired
	//	private CampaignConfigurationDao campaignConfigurationDao;
	
	@Override
	public LotteryResultBean doVote(@Size(min = 7, max = 128) String userId,
			@Size(min = 2, max = 10) String territory) {
		LotteryResultBean result = new LotteryResultBean();
		
		int winnerPerDay = 40;// campaignConfigurationDao.getWinnerPerDay(territory, campaignId..);
		
		// better random / secure, external, or a different method.
		Random rnd = new Random(40);
		int drawn = rnd.nextInt(winnerPerDay);
		
		if (0 == drawn) {
			result.setResultCode(LotteryResultBean.LOTTERY_RESULT_WON);
		} else {
			result.setResultCode(LotteryResultBean.LOTTERY_RESULT_LATER);
		}
		
		return result;
	}

	@Override
	public void addToWinnerSummary(@Size(min = 7, max = 128) String userId, @Size(min = 2, max = 10) String territory,
			@Pattern(regexp = "[0-9A-Z]{10}") String couponCode/*, couponRedeemTimestamp..*/) {
		
		// TODO: winnerSummaryBean = new winnerSummaryBean(userid, territory, couponcode, couponRedeemTimestamp)
		// TODO: some_deferring_service.enqueue(winnerSummaryBean)
		
	}

}
