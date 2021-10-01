delete from tree;

ALTER SEQUENCE ITEM_VERSION_SEQ RESTART;

CREATE OR REPLACE FUNCTION generate_roots() RETURNS integer [] AS $generate_roots$

declare 
  i integer;
  j integer[];

BEGIN
	i:=1;
  	while i < 11 loop  -- 10 trees :)
  	   insert into tree (id, name, version) values (i, concat(i,'_tree'),nextval('ITEM_VERSION_SEQ'));
	   insert into items (id, name, tree_id, parent_id, version) values (i, concat(i,'_item'),i,null,nextval('ITEM_VERSION_SEQ'));
	   j := j || i;
	   i := i + 1;
	end loop;   
	return j;
END;
$generate_roots$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION generate_level(nums integer[]) RETURNS integer [] AS $generate_level$

declare 
  i integer;
  j integer[];
  parent integer;
  curr_tree integer;
  min_itemid integer;

BEGIN
	SELECT max(x) into min_itemid FROM unnest(nums) as x;
	min_itemid := min_itemid + 1;
  	foreach parent in array nums loop
  		select COALESCE((SELECT tree_id  FROM items where id=parent), parent) into curr_tree;
  		i:=0;
  		while i < 10 loop  
		   insert into items (id, name, tree_id, parent_id, version) values (min_itemid, concat(min_itemid,'_item'),curr_tree,parent,nextval('ITEM_VERSION_SEQ'));
		   i := i + 1;
		   j := j || min_itemid;
		   min_itemid := min_itemid + 1;
		end loop;   
	END loop;
	return j;
END;
$generate_level$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION generate_data() RETURNS integer AS $generate_data$

declare 
  i integer;
  result integer[];	

BEGIN
	i := 0;
	
	result := generate_roots();
	while i < 5 loop  -- depth of generated trees
		result := generate_level(result);
		i := i + 1;
	end loop;
	
	return i;
END;
$generate_data$ LANGUAGE plpgsql;

select generate_data() as TREE_DEPTH;
commit;
select count(*) from items;