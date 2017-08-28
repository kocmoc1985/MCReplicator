create table recipe(
recipe_id varchar(30) not NULL,
revision integer not NULL,
primary key (recipe_id)
);

create table raw_material(
item_code varchar(30) not NULL,
item_name varchar(30) not NULL,
primary key (item_code)
);

create table vendor(
vendor_id integer not NULL,
vendor_name varchar(30) not NULL,
vendor_address varchar(200) not NULL,
primary key (vendor_id)
);


create table recipe_raw_material(
recipe_id varchar(30) not NULL,
item_code varchar(30) not NULL,
weight varchar(15) not NULL,
foreign key (recipe_id) references recipe(recipe_id),
foreign key (item_code) references raw_material(item_code)
);


create table raw_material_vendor(
item_code  varchar(30) not NULL,
vendor_id integer not NULL,
item_price  float not NULL,
changed_date date not null,
preffered_vendor boolean not null,
foreign key (item_code) references raw_material(item_code),
foreign key (vendor_id) references vendor(vendor_id)
);

/*================================================================= */

insert into recipe values ('9300165613','3');
insert into recipe values ('9300784651','2');
insert into recipe values ('9300784AVB','1');
insert into recipe values ('930087616F','1');
insert into recipe values ('9307894132','2');

insert into raw_material values ('A25','Chem1');
insert into raw_material values ('A45','Chem2');
insert into raw_material values ('A55','Chem3');
insert into raw_material values ('A65','Chem4');
insert into raw_material values ('A75','Chem5');
insert into raw_material values ('A85','Chem6');

insert into vendor values (589,'ABF','');
insert into vendor values (621,'DGG','');
insert into vendor values (635,'FVB','');

insert into recipe_raw_material values ('9300165613','A25','5kg');
insert into recipe_raw_material values ('9300165613','A45','2kg');
insert into recipe_raw_material values ('9300165613','A55','4kg');
insert into recipe_raw_material values ('9300165613','A65','1kg');
insert into recipe_raw_material values ('9300165613','A75','8kg');
insert into recipe_raw_material values ('9300165613','A85','1kg');
insert into recipe_raw_material values ('9300784651','A45','1kg');
insert into recipe_raw_material values ('9300784651','A65','1kg');
insert into recipe_raw_material values ('9300784651','A25','10kg');

insert into raw_material_vendor values ('A25',589,58.12,'2013-01-07',1);
insert into raw_material_vendor values ('A45',621,12,'2013-01-07',0);
insert into raw_material_vendor values ('A25',621,95,'2013-01-08',0);
insert into raw_material_vendor values ('A25',589,78,'2013-01-09',0);
insert into raw_material_vendor values ('A45',635,11,'2013-01-09',1);
insert into raw_material_vendor values ('A55',635,25,'2013-01-15',0);
insert into raw_material_vendor values ('A55',589,24,'2013-01-15',1);
insert into raw_material_vendor values ('A65',589,14,'2013-01-15',1);
insert into raw_material_vendor values ('A75',589,16,'2013-01-15',1);
insert into raw_material_vendor values ('A85',589,23,'2013-01-15',0);
insert into raw_material_vendor values ('A85',635,22,'2013-01-15',1);

/*================================================================= */

/*===The Basic One============= */
select recipe_raw_material.item_code,recipe_raw_material.recipe_id, raw_material_vendor.item_price,
raw_material_vendor.vendor_id,raw_material_vendor.preffered_vendor,raw_material_vendor.changed_date
from raw_material
inner join recipe_raw_material on raw_material.item_code = recipe_raw_material.item_code
inner join raw_material_vendor on raw_material.item_code = raw_material_vendor.item_code
where recipe_raw_material.recipe_id = '9300165613' order by raw_material_vendor.changed_date asc

/*===Counting total cost of all materials for a recipe with "preffered tag"===== */
select sum(raw_material_vendor.item_price) as total_cost
from raw_material
inner join recipe_raw_material on raw_material.item_code = recipe_raw_material.item_code
inner join raw_material_vendor on raw_material.item_code = raw_material_vendor.item_code
where recipe_raw_material.recipe_id = '9300165613' and raw_material_vendor.preffered_vendor = 1

/*===Find preffered vendor for material A25===== */
select * from raw_material
inner join raw_material_vendor on raw_material_vendor.item_code = raw_material.item_code
where raw_material.item_code ='A25' and preffered_vendor = 1


/*================================================================= */

create table item_price(
item_code  varchar(30) not NULL,
vendor_id integer not NULL,
item_price  float not NULL,
changed_date date not null,
foreign key (item_code) references raw_material(item_code),
foreign key (vendor_id) references vendor(vendor_id)
);



