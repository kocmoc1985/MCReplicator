create table interf(
VHMFNO varchar(60),
VHMBNO int,
VHPRNO varchar(60),
TPCODE varchar(60),
TCODE varchar(60),
TVER varchar(60),
TTRAIL int,
TAVALUE decimal(7,3),
TDATETIME datetime,
LSL decimal(7,3),
USL decimal(7,3),
UCL decimal(7,3),
LCL decimal(7,3),
UNITS varchar(10),
QCSTATUS varchar(20),
ExportTime datetime
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