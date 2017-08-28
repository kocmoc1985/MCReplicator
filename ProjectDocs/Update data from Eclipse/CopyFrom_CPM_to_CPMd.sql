go
delete from cpmd.dbo.MCCPingred
INSERT INTO cpmd.dbo.MCCPINGRED
SELECT     CODE, BASECODE, XREFCODE, [GROUP], DESCR, CASNO, BRMA, HOSTCODE, CHEMNAME, TRADENAME, FORM, PRODUCER, SUPPLIER, PERCRUBBER, 
                      PERCRUBTOL, PERCACTMAT, VISCTEMP, VISCML, VISCMLTOL, DENSITY, DENSITYTOL, PRICEKG1, PRICEDATE1, PRICEKG2, PRICEDATE2, PRICEKG3, PRICEDATE3, 
                      PRICEKG4, PRICEDATE4, ARTICLENO, STATUS, CLASS, INTSAFETY, NOTE, FREEINFO1, FREEINFO2, FREEINFO3, FREEINFO4, FREEINFO5, FREEINFO6, FREEINFO7, 
                      FREEINFO8, FREEINFO9, FREEINFO10, FREEINFO11, FREEINFO12, LASTUPDATE, UPDATEDBY
FROM         cpm.dbo.CPIngred	




go
delete from [cpmd].[dbo].[IngredtradeName]

INSERT INTO [cpmd].[dbo].[IngredtradeName]
           ([code]
           ,[TRADENAME]
           ,[PRODUCER]
           ,[SUPPLIER]
           ,[CASNO]
           ,[Price]
        )
    
        SELECT    distinct  CODE, TRADENAME, PRODUCER, SUPPLIER, CASNO, 
                      CASE WHEN PRICEKG1 + PRICEKG2 <> PRICEKG1 THEN PRICEKG2 WHEN PRICEKG1 + PRICEKG2 = PRICEKG1 THEN PRICEKG1 END AS PRICE 
FROM         [cpm].dbo.CPIngred


go 
delete from [cpmd].dbo.MCCPMIXCTR
INSERT INTO [cpmd].[dbo].MCCPMIXCTR
SELECT     MIXERID, CODE, DESCR, ORIGINCMP, ORIGINDEV, STATUS, MIXINFO, LASTUPDATE, UPDATEDBY
FROM         cpm.dbo.CPMIXCTR
	

go
delete from [cpmd].dbo.MCCPMIXSET
INSERT INTO [cpmd].[dbo].MCCPMIXSET
SELECT     CODE, NAME, VOLUME, COST, WASTE, DUST, STARTTIME, CYCLEOVH, DECIMALS, NOTE1, NOTE2, NOTE3, NOTE4, NOTE5, NOTE6, NOTE7, NOTE8, NOTE9, NOTE10, 
                      PLANFORMAT, LASTUPDATE
FROM         cpm.dbo.CPMIXSET	RETURN

go
delete from [cpmd].dbo.MCCPPLAN
insert INTO            [cpmd].dbo.MCCPPLAN
SELECT     PLANDATE, PLANID, ORDERNO, RCODE, MTYPE, STATUS, BATCHQTY, PRODQTY, FIRSTBATCH, PRIORITY, CANCELQTY, BOOKEDQTY, PRODDATE, ORIGIN, 
                      MODIFIED, REMARK, LASTUPDATE, UPDATEDBY, null as release
FROM         cpm.dbo.CPPLAN

go
delete from [cpmd].dbo.MCCPRECIPE
INSERT INTO [cpmd].[dbo].MCCPRECIPE
SELECT     CODE, RELEASE, DESCR, STATUS, CLASS, [GROUP], SCRAMBLED, DEVELDATE, DEVELBY, CUSTOMER, FREEINFO1, FREEINFO2, FREEINFO3, FREEINFO4, 
                      FREEINFO5, FREEINFO6, FREEINFO7, FREEINFO8, FREEINFO9, FREEINFO10, FREEINFO11, FREEINFO12, MIXCODE, LOADFACTOR, MIXTIME1, MIXTIME2, 
                      CTRLCODE, MATERIAL1, MATERIAL2, MATERIAL3, MATERIAL4, MATERIAL5, MATERIAL6, MATERIAL7, MATERIAL8, MATERIAL9, MATERIAL10, MATERIAL11, 
                      MATERIAL12, MATERIAL13, MATERIAL14, MATERIAL15, MATERIAL16, MATERIAL17, MATERIAL18, MATERIAL19, MATERIAL20, HOSTCODE, NOTE, MIXPROC, 
                      LASTUPDATE, UPDATEDBY
FROM         cpm.dbo.CPRECIPE 

go
delete from [cpmd].dbo.MCCPTPROC
INSERT INTO [cpmd].[dbo].MCCPTPROC
SELECT     CODE, DESCRIPT, NORM, STATUS, CLASS, [GROUP], TESTVAR1, TESTVAR2, TESTVAR3, TESTVAR4, TESTVAR5, TESTVAR6, TESTVAR7, TESTVAR8, TESTVAR9, 
                      TESTVAR10, TESTVAR11, TESTVAR12, TESTVAR13, TESTVAR14, TESTVAR15, REPORT, VERSION, NOTE, LASTUPDATE, UPDATEDBY
FROM         cpm.dbo.CPTPROC
	
go
delete from [cpmd].dbo.MCCPTRADE
INSERT INTO [cpmd].[dbo].[MCCPTRADE]
           ([NAME]
           ,[ADDRESS]
           ,[CODE]
           ,[CITY]
           ,[COUNTRY]
           ,[PHONE]
           ,[FAX]
           ,[LASTUPDATE]
           ,[SUPPLIERNO])
SELECT [NAME]
      ,[ADDRESS]
      ,[CODE]
      ,[CITY]
      ,[COUNTRY]
      ,[PHONE]
      ,[FAX]
      ,[LASTUPDATE]
	,[SUPPLIERNO]
  FROM [CPM].[dbo].[CPTRADE]

go
delete from [cpmd].dbo.MCGLGroup
INSERT INTO [cpmd].[dbo].MCGLGROUP
SELECT     SCOPE, GROUPCODE, DESCR, Null as descrnew, null as Main_Group, null as Main_Info
FROM         cpm.dbo.GLGROUP


go
delete from [cpmd].dbo.MCGLTables
INSERT INTO [cpmd].[dbo].MCGLTABLES
SELECT     SCOPE, CODE, TABLEFIELD
FROM         [cpm].[dbo].GLTABLES	

go
delete from [cpmd].dbo.MCPLCCOMM
INSERT INTO [cpmd].[dbo].MCPLCCOMM
SELECT     SCOPE, CODE, TABLEFIELD
FROM          [cpm].[dbo].PLCCOMM






go
delete from [cpmd].[dbo].[MCCPWARE]
INSERT INTO [cpmd].[dbo].[MCCPWARE]
           ([CODE]
           ,[LOTNO]
           ,[BOXNO]
           ,[LOCATION1]
           ,[LOCATION2]
           ,[STORETIME]
           ,[STTEMPMIN]
           ,[STTEMPMAX]
           ,[STOREDATE]
           ,[STDATEEXT]
           ,[ACTSTOCK]
           ,[MINSTOCK]
           ,[LASTUSED]
           ,[USAGEDATE]
           ,[STUPDATE]
           ,[FORM]
           ,[QUANTITY]
           ,[QUANTUNIT]
           ,[ORDER]
           ,[ORDERUNIT]
           ,[SILOID]
           ,[BALANCEID]
           ,[SWFINE]
           ,[SWVERYFINE]
           ,[STOPSIGNAL]
           ,[TOLERANCE]
           ,[LASTUPDATE])
      SELECT [CODE]
      ,[LOTNO]
      ,[BOXNO]
      ,[LOCATION1]
      ,[LOCATION2]
      ,[STORETIME]
      ,[STTEMPMIN]
      ,[STTEMPMAX]
      ,[STOREDATE]
      ,[STDATEEXT]
      ,[ACTSTOCK]
      ,[MINSTOCK]
      ,[LASTUSED]
      ,[USAGEDATE]
      ,[STUPDATE]
      ,[FORM]
      ,[QUANTITY]
      ,[QUANTUNIT]
      ,[ORDER]
      ,[ORDERUNIT]
      ,[SILOID]
      ,[BALANCEID]
      ,[SWFINE]
      ,[SWVERYFINE]
      ,[STOPSIGNAL]
      ,[TOLERANCE]
      ,[LASTUPDATE]
  FROM [CPM].[dbo].[CPWare]


go
delete from [cpmd].[dbo].[IngredCPCASMC]

INSERT INTO [cpmd].[dbo].[IngredCPCASMC]
           ([CODE]
           ,[CASNO]
           ,[BOILTEMP]
           ,[MELTTEMP]
           ,[FLASHTEMP]
           ,[IGNITTEMP]
           ,[DECOMPTEMP]
           ,[SOLUB]
           ,[HYGRO]
           ,[MACPPM]
           ,[MACMGM3]
           ,[TOXIC]
           ,[INFLAM]
           ,[EXPLO]
           ,[CORRO]
           ,[OXYD]
           ,[CARC]
           ,[VENT]
           ,[FIRE]
           ,[DUST]
           ,[FACE]
           ,[EYE]
           ,[GLOVES]
           ,[SPEC]
           ,[REMARK1]
           ,[REMARK2]
           ,[REMARK3]
           ,[LASTUPDATE])
    SELECT [CODE]
      ,[CASNO]
      ,[BOILTEMP]
      ,[MELTTEMP]
      ,[FLASHTEMP]
      ,[IGNITTEMP]
      ,[DECOMPTEMP]
      ,[SOLUB]
      ,[HYGRO]
      ,[MACPPM]
      ,[MACMGM3]
      ,[TOXIC]
      ,[INFLAM]
      ,[EXPLO]
      ,[CORRO]
      ,[OXYD]
      ,[CARC]
      ,[VENT]
      ,[FIRE]
      ,[DUST]
      ,[FACE]
      ,[EYE]
      ,[GLOVES]
      ,[SPEC]
      ,[REMARK1]
      ,[REMARK2]
      ,[REMARK3]
      ,[LASTUPDATE]
  FROM [CPM].[dbo].[CPCAS]

