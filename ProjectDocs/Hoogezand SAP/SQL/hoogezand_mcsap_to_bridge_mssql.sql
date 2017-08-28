create table item_price_b(
ID int IDENTITY(1,1),
itemCode varchar(30) not NULL,
itemPrice float not NULL,
dateLastRolled datetime,
dateExport datetime,
dateProcessed datetime,
status varchar(200),
constraint pk_item_price primary key(itemCode)
);

create table vendor_b(
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
status varchar(200),
constraint pk_vendor_id primary key(vendorNo)
);


create table vendor_contact_b(
ID int IDENTITY(1,1),
vendorNo varchar(20) not NULL,
contactName varchar(50),
position varchar(90),
phone varchar(50),
email varchar(50),
dateExport datetime,
dateProcessed datetime,
status varchar(200),
constraint pk_vendor_contact primary key(ID),
constraint fk_vendor_contact foreign key (vendorNo) references vendor_b(vendorNo)
);

create table interface_trigger_back(
ID int IDENTITY(1,1),
dataStream varchar(50) not NULL,
sender varchar(50),
reciever varchar(50),
dateSend datetime,
dateProcessed datetime,
status varchar(200)
);
