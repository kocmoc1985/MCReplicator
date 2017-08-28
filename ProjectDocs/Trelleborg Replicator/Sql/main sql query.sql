SELECT     TOP 100 PERCENT Result.OrderCode AS VHMFNO, Result.BatchNo AS VHMBNO, Limits.Quality AS VHPRNO, 
                      [Plan].Profile AS TPCODE, [Procedure].TestCode AS TCODE, TAG.Name AS TVER, Result.TestNo AS TTRAIL, 
                      ResultTest.Result AS TAVALUE, Result.TestDate, LimitTest.LSL, LimitTest.USL, LimitTest.LWL AS UCL, 
                      LimitTest.UWL AS LCL, TAG.Unit AS UNITS, Result.Status AS QCSTATUS, { fn NOW() } AS ExportTime
FROM         Limits INNER JOIN
                      LimitTest ON Limits.ID = LimitTest.LimitID INNER JOIN
                      [Procedure] ON Limits.ProcID = [Procedure].ID INNER JOIN
                      ProcTest ON LimitTest.ProctestID = ProcTest.ID AND [Procedure].ID = ProcTest.ProcID INNER JOIN
                      Result ON Limits.ID = Result.LimitID INNER JOIN
                      ResultTest ON LimitTest.ID = ResultTest.LimitTestID AND Result.ID = ResultTest.ResultID INNER JOIN
                      TAG ON ProcTest.TagID = TAG.ID INNER JOIN
                      [Plan] ON Result.OrderCode = [Plan].OrderCode
WHERE     (Result.Status <> 'DELETED') AND (Result.TestDate > CONVERT(DATETIME, '2009-12-12 00:00:00', 102))
ORDER BY Result.OrderCode DESC, Result.BatchNo, [Procedure].TestCode, TAG.Name, Result.TestNo