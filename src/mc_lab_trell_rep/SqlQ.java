/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mc_lab_trell_rep;

import Supplementary.HelpM;

/**
 *
 * @author KOCMOC
 */
public class SqlQ {

    /**
     * 
     * @param date - date of the last export
     * @return 
     */
    public static String get_select_query_1(String date) {
        if (date.isEmpty()) {
            date = "2009-12-12 00:00:00";
        }
        //
        String rst = "SELECT TOP 100 PERCENT Result.OrderCode AS VHMFNO, Result.BatchNo AS VHMBNO, Limits.Quality AS VHPRNO, \n"
                + " [Plan].Profile AS TPCODE, [Procedure].TestCode AS TCODE, TAG.Name AS TVER, Result.TestNo AS TTRAIL, \n"
                + " ResultTest.Result AS TAVALUE, Result.TestDate, LimitTest.LSL, LimitTest.USL, LimitTest.LWL AS UCL, \n"
                + " LimitTest.UWL AS LCL, TAG.Unit AS UNITS, Result.Status AS QCSTATUS, { fn NOW() } AS ExportTime"
                //==
                + " FROM Limits INNER JOIN\n"
                + " LimitTest ON Limits.ID = LimitTest.LimitID INNER JOIN\n"
                + " [Procedure] ON Limits.ProcID = [Procedure].ID INNER JOIN\n"
                + " ProcTest ON LimitTest.ProctestID = ProcTest.ID AND [Procedure].ID = ProcTest.ProcID INNER JOIN\n"
                + " Result ON Limits.ID = Result.LimitID INNER JOIN\n"
                + " ResultTest ON LimitTest.ID = ResultTest.LimitTestID AND Result.ID = ResultTest.ResultID INNER JOIN\n"
                + " TAG ON ProcTest.TagID = TAG.ID INNER JOIN\n"
                + " [Plan] ON Result.OrderCode = [Plan].OrderCode"
                //===
                + " WHERE (Result.Status <> 'DELETED')"
//                + " AND (Result.TestDate > CONVERT(DATETIME, '2014-12-01 00:00:00', 102))"
                + " AND (Result.TestDate > CONVERT(DATETIME,'" + date + "',102))"
                + " ORDER BY Result.OrderCode DESC, Result.BatchNo,"
                + " [Procedure].TestCode, TAG.Name, Result.TestNo";
        return rst;
    }
    
    public static String get_select_query_2(String dateFrom,String dateTo) {
        if (dateFrom.isEmpty()) {
            dateFrom = "2009-12-12 00:00:00";
        }
        //
        String rst = "SELECT TOP 100 PERCENT Result.OrderCode AS VHMFNO, Result.BatchNo AS VHMBNO, Limits.Quality AS VHPRNO, \n"
                + " [Plan].Profile AS TPCODE, [Procedure].TestCode AS TCODE, TAG.Name AS TVER, Result.TestNo AS TTRAIL, \n"
                + " ResultTest.Result AS TAVALUE, Result.TestDate, LimitTest.LSL, LimitTest.USL, LimitTest.LWL AS UCL, \n"
                + " LimitTest.UWL AS LCL, TAG.Unit AS UNITS, Result.Status AS QCSTATUS, { fn NOW() } AS ExportTime"
                //==
                + " FROM Limits INNER JOIN\n"
                + " LimitTest ON Limits.ID = LimitTest.LimitID INNER JOIN\n"
                + " [Procedure] ON Limits.ProcID = [Procedure].ID INNER JOIN\n"
                + " ProcTest ON LimitTest.ProctestID = ProcTest.ID AND [Procedure].ID = ProcTest.ProcID INNER JOIN\n"
                + " Result ON Limits.ID = Result.LimitID INNER JOIN\n"
                + " ResultTest ON LimitTest.ID = ResultTest.LimitTestID AND Result.ID = ResultTest.ResultID INNER JOIN\n"
                + " TAG ON ProcTest.TagID = TAG.ID INNER JOIN\n"
                + " [Plan] ON Result.OrderCode = [Plan].OrderCode"
                //===
                + " WHERE (Result.Status <> 'DELETED')"
//                + " AND (Result.TestDate > CONVERT(DATETIME, '2014-12-01 00:00:00', 102))"
                + " AND (Result.TestDate > CONVERT(DATETIME,'" + dateFrom + "',102))"
                + " AND (Result.TestDate < CONVERT(DATETIME,'" + dateTo + "',102))" // OBS! ERASE THIS 
                + " ORDER BY Result.OrderCode DESC, Result.BatchNo,"
                + " [Procedure].TestCode, TAG.Name, Result.TestNo";
        return rst;
    }
    
//    public static void main(String[] args) {
//        System.out.println("" + get_select_query_1("2014-03-25 13:00:00"));
//
//    }

    public static String insert_into_table_to_copy(TableToCopy ttc) {
        String rst = "insert into " + DBT_trell.MCTOTRELL_TABLE_NAME + " values ("
                + "'" + ttc.getVHMFNO() + "'"
                + "," + ttc.getVHMBNO() + ""
                + ",'" + ttc.getVHPRNO() + "'"
                + ",'" + ttc.getTPCODE() + "'"
                + "," + ttc.getTCODE() + ""
                + ",'" + ttc.getTVER() + "'"
                + "," + ttc.getTTRAIL() + ""
                + "," + ttc.getTAVALUE() + ""
                + ",'" + ttc.getTDATETIME() + "'"
                + "," + ttc.getLSL() + ""
                + "," + ttc.getUSL() + ""
                + "," + ttc.getUCL() + ""
                + "," + ttc.getLCL() + ""
                + ",'" + ttc.getUNITS() + "'"
                + ",'" + ttc.getQCSTATUS() + "'"
                + ",'" + HelpM.get_proper_date_time_same_format_on_all_computers() + "')";
        return rst;
    }

    public static String get_latest_export_date() {
        String rst = "select top 1 * from " + DBT_trell.MCTOTRELL_TABLE_NAME
                + " order by " + "ExportTime" + " desc";
        return rst;
    }

    
}
