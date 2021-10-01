-- helper to throw:
-- hint is used for success, detail for retryable.. not beautiful.
-- But there is no context-data holder in it.. and parsing a composite message, well.. still not beautiful.
drop function IF EXISTS cmp_redeem_coupon_raise_code(text, boolean, boolean);
CREATE FUNCTION cmp_redeem_coupon_raise_code(code text, success boolean=false, retryable boolean=false)
  RETURNS void
  LANGUAGE plpgsql AS
$cmp_redeem_coupon_raise_code$
BEGIN
   RAISE EXCEPTION '%', $1 using hint=success::text, detail=retryable::text;
END
$cmp_redeem_coupon_raise_code$;

--
------------------------------------------------------------------------------------------------------
--
------------------------------------------------------------------------------------------------------
--

drop function IF EXISTS cmp_redeem_coupon_init(text, integer, text);
CREATE function cmp_redeem_coupon_init(
		dayCode text,
	 	newVersion integer,
		atTerritory text
	)
	RETURNS TABLE(
		sumVersion integer,
		sumRemains integer,
		winnerMod integer,
		winnerPerDay integer,
	 	dailyVersion integer,
		dailyRemains integer
	) 
AS $cmp_redeem_coupon_init$
declare 
	sumVersion integer;
	sumRemains integer;
	winnerMod integer;
	winnerPerDay integer;
 	dailyVersion integer;
	dailyRemains integer;
begin
	
	select version, remainingItems, winnerModulus, winnerPerDayLimit into sumVersion, sumRemains, winnerMod, winnerPerDay from CMPN_COUPON_CONFIG_V1 cfg where cfg.territory=atTerritory; 
	IF sumVersion IS NULL OR sumRemains is null then
		select cmp_redeem_coupon_raise_code('NO_SUCH_REGION_CONFIG'::VARCHAR, false, false);
	END IF;
	if sumRemains < 1 then
		select cmp_redeem_coupon_raise_code('NO_BALLS'::VARCHAR, false, false);
	end if;

	select version, remainingWinners into dailyVersion, dailyRemains from CMPN_COUPON_DAILY_COUNTER_V1 cfg where cfg.territory=atTerritory and cfg.onDate=dayCode; 
	IF dailyVersion IS NULL then
		insert into CMPN_COUPON_DAILY_COUNTER_V1 (territory, onDate, remainingWinners, version) values (atTerritory, dayCode, winnerPerDay, newVersion);
		select version, remainingWinners into dailyVersion, dailyRemains from CMPN_COUPON_DAILY_COUNTER_V1 cfg where cfg.territory=atTerritory and cfg.onDate=dayCode;
		IF dailyVersion <> newVersion then
			select cmp_redeem_coupon_raise_code('INCONSISTENT_DAILY_LIMITS'::VARCHAR, false, true);
		end if;
	END IF;
	if dailyRemains < 1 then
		select cmp_redeem_coupon_raise_code('NO_BALLS_TODAY'::VARCHAR, false, false);
	end if;

	return QUERY SELECT sumVersion, sumRemains, winnerMod, winnerPerDay, dailyVersion, dailyRemains;
		
END;
$cmp_redeem_coupon_init$ LANGUAGE plpgsql;

--
------------------------------------------------------------------------------------------------------
--
------------------------------------------------------------------------------------------------------
--

drop function IF EXISTS cmp_redeem_coupon_update_limits(text, integer, integer, integer, text);
CREATE function
	cmp_redeem_coupon_update_limits(
		dayCode text,
	 	newVersion integer,
	 	dailyVersion integer,
	 	sumVersion integer,
		atTerritory text
	)
	RETURNS void
AS $cmp_redeem_coupon_update_limits$
declare 
	changeCount integer;
begin

	update CMPN_COUPON_DAILY_COUNTER_V1 set 
		remainingWinners = remainingWinners-1,
		version = newVersion
	where territory = atTerritory and onDate = dayCode and version = dailyVersion;
	GET DIAGNOSTICS changeCount = ROW_COUNT;
	if changeCount <> 1 then
		select cmp_redeem_coupon_raise_code('CONCURRENT_DAILY_COUNT_CHG'::VARCHAR, false, true);
	end if;
	
	
	update CMPN_COUPON_CONFIG_V1 set 
		remainingItems = remainingItems-1,
		version = newVersion
	where territory = atTerritory and version = sumVersion;
	GET DIAGNOSTICS changeCount = ROW_COUNT;
	if changeCount <> 1 then
		select cmp_redeem_coupon_raise_code('CONCURRENT_TOTAL_COUNT_CHG'::VARCHAR, false, true);
	end if;
	

	
END;
$cmp_redeem_coupon_update_limits$ LANGUAGE plpgsql;

--
------------------------------------------------------------------------------------------------------
--
------------------------------------------------------------------------------------------------------
--

drop function IF EXISTS cmp_redeem_coupon_transaction(text, text, text);
CREATE OR REPLACE function
	cmp_redeem_coupon_transaction(
		in atTerritory text,
		in couponcode text,
		in userid text
	)
	RETURNS TABLE(
		-- programmatically usable response code
		RESULTCODE VARCHAR(50),
		-- generally, success or failure
		SUCCESS BOOLEAN,
		-- automatically retryable.. the problem might resolve after a retry.. concurrentmod..
		RETRYABLE BOOLEAN
	) 
AS $cmp_redeem_coupon_transaction$
declare 
	dayCode text;
 	newVersion integer;
 	couponSegment text;

	sumVersion integer;
	sumRemains integer;
	winnerMod integer;
	winnerPerDay integer;
 	dailyVersion integer;
	dailyRemains integer;
begin

	dayCode:= to_char(now()::date, 'YYYYMMDD');
	newVersion := nextval('CMPN_COUPON_DAILY_COUNTER_V1_version_SEQ');

	select * from cmp_redeem_coupon_init(dayCode, newVersion, atTerritory) into sumVersion, sumRemains, winnerMod, winnerPerDay, dailyVersion, dailyRemains;
	
	couponSegment := concat('CMPN_COUPON_REDEEMED_V1_', substring(md5(couponCode) from 1 for 1));
	EXECUTE concat('insert into ',couponSegment,' (territory, couponCode, userId) values ($1, $2, $3)')
		USING atTerritory, couponCode, userId;
	
	perform cmp_redeem_coupon_update_limits(dayCode, newVersion, dailyVersion, sumVersion, atTerritory);
	
	return QUERY SELECT 'OK'::VARCHAR, true, false;
END;
$cmp_redeem_coupon_transaction$ LANGUAGE plpgsql;

--
------------------------------------------------------------------------------------------------------
--
------------------------------------------------------------------------------------------------------
--

drop function IF EXISTS cmp_redeem_coupon(text, text, text);
CREATE OR REPLACE function
	cmp_redeem_coupon(
		in atTerritory text,
		in couponcode text,
		in userid text
	)
	RETURNS TABLE(
		-- programmatically usable response code
		RESULTCODE VARCHAR(50),
		-- generally, success or failure
		SUCCESS varchar(500),
		-- automatically retryable.. the problem might resolve after a retry.. concurrentmod..
		RETRYABLE varchar(500)
	) 
AS $cmp_redeem_coupon$
declare 
	resCode varchar(500);
 	resSuccess varchar(500); -- boolean / cast / transform.. beautyful exceptions.
 	resRetryable varchar(500); -- boolean / cast / transform.. beautyful exceptions.
begin
	-- time zone of the user..
	IF couponCode IS NULL then
		return QUERY SELECT 'NO_PARAM_COUPONCODE'::VARCHAR, false::varchar, false::varchar;
	END IF;
	IF atTerritory IS NULL then
		return QUERY SELECT 'NO_PARAM_TERRITORY'::VARCHAR, false::varchar, false::varchar;
	END IF;
	IF userId IS NULL then
		return QUERY SELECT 'NO_PARAM_USER'::VARCHAR, false::varchar, false::varchar;
	END IF;

	select resCode, resSuccess, resRetryable from cmp_redeem_coupon_transaction(atTerritory, couponCode, userId) into resCode, resSuccess, resRetryable;

	return QUERY SELECT 'OK'::varchar, true::varchar, false::varchar;

	exception 
		when sqlstate '23505' then
			-- most possibly.. but a todo.
			return QUERY SELECT 'ALREADY_ADDED'::VARCHAR, false::varchar, false::varchar;
		when others then 
			-- not beautifully raised exceptions come out here (cmp_redeem_coupon_raise_code)
			-- todo care about our not-beautiful-exceptions differently than other exceptions..
		    get stacked diagnostics
		        resCode = message_text,
		        resSuccess = pg_exception_hint,
		        resRetryable = pg_exception_detail;
			return QUERY SELECT resCode::varchar, resSuccess::varchar, resRetryable::varchar;

END;
$cmp_redeem_coupon$ LANGUAGE plpgsql;

--select * from cmp_redeem_coupon('hu', 'OEUIOEUI17', 'hu@hu.hu');
--select * from CMPN_COUPON_REDEEMED_V1_3;
--select * from CMPN_COUPON_DAILY_COUNTER_V1;

