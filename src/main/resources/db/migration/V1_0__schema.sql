
CREATE TABLE IF NOT EXISTS CMPN_USERS_V1 (
  userId VARCHAR(128) NOT NULL,
  territory VARCHAR(10) NOT NULL,
  -- NVARCHAR.. 
  name VARCHAR(128) NOT NULL,
  address VARCHAR(128) NOT NULL
);
ALTER TABLE CMPN_USERS_V1 ADD CONSTRAINT CMPN_USERS_V1_PK PRIMARY KEY (userId);


CREATE TABLE IF NOT EXISTS CMPN_COUPON_CONFIG_V1 (
  territory VARCHAR(10) NOT NULL,
  -- campaign-id..
  winnerModulus INTEGER NOT NULL,
  remainingItems INTEGER NOT NULL,
  winnerPerDayLimit INTEGER NOT NULL,
  version INTEGER NOT NULL
);
ALTER TABLE CMPN_COUPON_CONFIG_V1 ADD CONSTRAINT CMPN_COUPON_CONFIG_V1_PK PRIMARY KEY (territory);

CREATE TABLE IF NOT EXISTS CMPN_COUPON_DAILY_COUNTER_V1 (
  territory VARCHAR(10) NOT NULL,
  onDate VARCHAR(8) DEFAULT TO_CHAR((now() at time zone 'utc')::DATE, 'yyyymmdd'),
  -- campaign-id..
  remainingWinners INTEGER NOT NULL,
  version INTEGER NOT NULL
);


CREATE TABLE IF NOT EXISTS CMPN_COUPON_WINNER_SUMMARY_V1 (
  territory VARCHAR(10) NOT NULL,
  couponCode VARCHAR(10) NOT NULL,
  userId VARCHAR(128) NOT NULL,
  redeemedAt timestamp without time zone default (now() at time zone 'utc')
  -- + email..
);




CREATE OR REPLACE FUNCTION create_create_coupon_redeem_segment_tables() RETURNS integer AS $create_create_coupon_redeem_segment_tables$
declare 
  ti integer;
  tl integer;
  segment text;
  segments TEXT[];
begin
	segments  := '{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"}';
	tl :=  array_length(segments, 1)+1;
	ti := 1;
	while ti < tl loop
		segment:=segments[ti];
		EXECUTE concat('DROP TABLE IF EXISTS CMPN_COUPON_REDEEMED_V1_', segment);
		EXECUTE concat(
				'CREATE TABLE CMPN_COUPON_REDEEMED_V1_', segment, ' (',
				' territory VARCHAR(10) NOT NULL,',
				' couponCode VARCHAR(10) NOT NULL,',
				' userId VARCHAR(128) NOT NULL,',
				' redeemedAt timestamp without time zone default (now() at time zone ''utc'')',
				')');
		EXECUTE concat(
				'ALTER TABLE CMPN_COUPON_REDEEMED_V1_', segment, ' ADD CONSTRAINT CMPN_COUPON_REDEEMED_V1_', segment, '_PK ',
				' PRIMARY KEY (couponCode)',
				'');
		ti := ti+1;
	end loop;
	return ti;
END;
$create_create_coupon_redeem_segment_tables$ LANGUAGE plpgsql;
select create_create_coupon_redeem_segment_tables();



CREATE INDEX CMPN_COUPON_DAILY_COUNTER_V1_I_territory ON CMPN_COUPON_DAILY_COUNTER_V1 (territory);
CREATE INDEX CMPN_COUPON_DAILY_COUNTER_V1_I_onDate ON CMPN_COUPON_DAILY_COUNTER_V1 (onDate ASC);
CREATE INDEX CMPN_COUPON_DAILY_COUNTER_V1_I_version ON CMPN_COUPON_DAILY_COUNTER_V1 (version);


CREATE SEQUENCE IF NOT EXISTS CMPN_COUPON_DAILY_COUNTER_V1_version_SEQ  MINVALUE 1 START 1;

