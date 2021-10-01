

CREATE OR REPLACE FUNCTION cmp_test_generate_users() RETURNS integer AS $cmp_test_generate_users$

declare 
  i integer;
  ti integer;
  territory text;
  territories TEXT[];

begin
	territories  := '{"de", "hu"}';
	i:=0;
	ti:=1;
	while ti < 3 loop
		territory:=territories[ti];
		while i < 1000 loop
			insert into CMPN_USERS_V1 (
				userid,
				territory,
				name,
				address
			) values (
				concat(territory, '.', i, '@', territory, '.', territory),
				territory,
				concat(territory, ' ', territory),
				concat(territory, '-', i)
			);
			i := i + 1;
		end loop;
		ti := ti+1;
	end loop;
	return ti*i;
END;
$cmp_test_generate_users$ LANGUAGE plpgsql;
select cmp_test_generate_users();

