


insert into CMPN_COUPON_CONFIG_V1 (
	territory,
	winnermodulus,
	remainingitems,
	winnerperdaylimit,
	version
) values (
	'de',
	40,
	10000,
	250,
	nextval('CMPN_COUPON_DAILY_COUNTER_V1_version_SEQ')
);

insert into CMPN_COUPON_CONFIG_V1 (
	territory,
	winnermodulus,
	remainingitems,
	winnerperdaylimit,
	version
) values (
	'hu',
	80,
	5000,
	100,
	nextval('CMPN_COUPON_DAILY_COUNTER_V1_version_SEQ')
);

select * from CMPN_COUPON_CONFIG_V1;
