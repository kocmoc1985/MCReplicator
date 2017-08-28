drop table recipe_raw_material;
drop table raw_material_vendor;
drop table item_price;
drop table vendor_contact;
drop table recipe;
drop table raw_material;
drop table vendor;
drop table interface_trigger;
/*================================================================= */
delete from recipe_raw_material;
delete from raw_material_vendor;
delete from item_price;
delete from vendor_contact;
delete from recipe;
delete from raw_material;
delete from vendor;
delete from interface_trigger;
/*================================================================= */
delete from recipe_raw_material;
delete from recipe;
delete from raw_material;
delete from interface_trigger;
/*================================================================= */

select top 1000 [MCSAP].[mcsap_mc].[recipe].* from [MCSAP].[mcsap_mc].[recipe]
where workInstructions like '%time %'

/*================================================================= */

create table recipe(
ID int IDENTITY(1,1),
parentItemCode varchar(30) not NULL,
familySubGroup varchar(20),
groupName varchar(50),
workInstructions varchar(2000),
description varchar(100),
recipeStatus varchar(5),
developedOn datetime,
lastUpdate datetime,
shelfLifeDays int,
solidity decimal(19,3),
tempDie1 varchar(10),
heightDie1 decimal(19,3),
firstOpDie1 varchar(30),
secondOpDie1 varchar(30),
numTopMix1 decimal(19,3),
sample1 varchar(10),
tempDie2 varchar(10),
heightDie2 decimal(19,3),
firstOpDie2 varchar(30),
secondOpDie2 varchar(30),
numTopMix2 decimal(19,3),
sample2 varchar(10),
dim_width varchar(10),
dim_thickness varchar(10),
rolls_input varchar(20),
rolls_stack varchar(20),
roll_mark varchar(20),
deliveryForm varchar(30),
package_ varchar(30),
batchPerUnit decimal(19,3),
remark1 varchar(50),
remark2 varchar(50),
remark3 varchar(50),
remark4 varchar(50),
dateExport datetime,
dateProcessed datetime,
status varchar(200)
);

create table raw_material(
ID int IDENTITY(1,1),
itemCode varchar(30) not NULL,
itemName varchar(100) not NULL,
prefferedLocation varchar(200),
shelfLifeDays int,
groupTechnologyCode varchar(20),
familyItemNumber varchar(100),
rawMaterialStatus varchar(5),
nmfc varchar(6),
nmfc_subCode varchar(2),
eccn varchar(15),
itemReference varchar(25),
dateExport datetime,
dateProcessed datetime,
status varchar(200)
);

create table vendor(
ID int IDENTITY(1,1),
vendorNo varchar(20) not NULL,
vendorName varchar(100) not NULL,
address varchar(200),
zipCode varchar(20),
city varchar(100),
country varchar(100),
phone varchar(50),
fax varchar(50),
email varchar(50),
website varchar(50), 
dateExport datetime,
dateProcessed datetime,
status varchar(200)
);

create table recipe_raw_material(
ID int IDENTITY(1,1),
parentItemCode varchar(30) not NULL,
itemCode varchar(30) not NULL,
pointOfUse varchar(8),
requiredQuantity decimal(19,3),
dateExport datetime,
dateProcessed datetime,
status varchar(200)
);

create table raw_material_vendor(
ID int IDENTITY(1,1),
itemCode varchar(30) not NULL,
vendorName varchar(50) not NULL,
tradeName varchar(100),
dateExport datetime,
dateProcessed datetime,
status varchar(200)
);

create table item_price(
ID int IDENTITY(1,1),
itemCode varchar(30) not NULL,
itemPrice float not NULL,
dateLastRolled datetime,
dateExport datetime,
dateProcessed datetime,
status varchar(200)
);

create table vendor_contact(
ID int IDENTITY(1,1),
vendorNo varchar(20) not NULL,
contactName varchar(50),
position varchar(90),
phone varchar(50),
email varchar(50),
dateExport datetime,
dateProcessed datetime,
status varchar(200)
);

create table interface_trigger(
ID int IDENTITY(1,1),
dataStream varchar(50) not NULL,
sender varchar(50),
reciever varchar(50),
dateSend datetime,
dateProcessed datetime,
status varchar(200)
);


insert into raw_material_vendor values ('A85',589,23,'2013-01-15',0);
insert into raw_material_vendor values ('A85',635,22,'2013-01-15',1);

/*================================================================= */
/*================================================================= */
/*================================================================= */



/*================================================================= */
/*===ThiS ARE OUTDATED, BUT VERY GOOD AS EXAMPLE==== */
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
/*================================================================= */

/*====Quiries for Rawmaterial table ====== */
select IngredMC1.CODE,DESCR,SILOID,BALANCEID,TRADENAME,LOCATION1,STOREDATE,STATUS,GRP,GROEPNAME from MCCPWARE
inner join IngredMC1 on MCCPWARE.CODE=IngredMC1.CODE COLLATE Latin1_General_CI_AS

select MCCPWARE.CODE,DESCR,SILOID,BALANCEID,TRADENAME,LOCATION1,STOREDATE,STATUS,GRP,GROEPNAME from MCCPWARE
right outer join IngredMC1 on MCCPWARE.CODE=IngredMC1.CODE COLLATE Latin1_General_CI_AS

/*====Quiries for Recipe ====== */




/*================================================================= */





