/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mc_lab_trell_rep;

/**
 *
 * @author KOCMOC
 */
public class TableToCopy {

    private final String VHMFNO;
    private final int VHMBNO;
    private final String VHPRNO;
    private final String TPCODE;
    private final String TCODE;
    private final String TVER;
    private final int TTRAIL;
    private final double TAVALUE;
    private final String TDATETIME;
    private final double LSL;
    private final double USL;
    private final double UCL;
    private final double LCL;
    private final String UNITS;
    private final String QCSTATUS;
    private final String ExportTime;

    public TableToCopy(String VHMFNO, int VHMBNO, String VHPRNO, String TPCODE, String TCODE, String TVER, int TTRAIL, double TAVALUE, String TDATETIME, double LSL, double USL, double UCL, double LCL, String UNITS, String QCSTATUS, String ExportTime) {
        this.VHMFNO = VHMFNO;
        this.VHMBNO = VHMBNO;
        this.VHPRNO = VHPRNO;
        this.TPCODE = TPCODE;
        this.TCODE = TCODE;
        this.TVER = TVER;
        this.TTRAIL = TTRAIL;
        this.TAVALUE = TAVALUE;
        this.TDATETIME = TDATETIME;
        this.LSL = LSL;
        this.USL = USL;
        this.UCL = UCL;
        this.LCL = LCL;
        this.UNITS = UNITS;
        this.QCSTATUS = QCSTATUS;
        this.ExportTime = ExportTime;
    }

    public String getExportTime() {
        return process(ExportTime);
    }

    public double getLCL() {
        return LCL;
    }

    public double getLSL() {
        return LSL;
    }

    public String getQCSTATUS() {
        return process(QCSTATUS);
    }

    public double getTAVALUE() {
        return TAVALUE;
    }

    public String getTCODE() {
        return process(TCODE).replace("'", "");
    }

    public String getTPCODE() {
        return process(TPCODE);
    }

    public int getTTRAIL() {
        return TTRAIL;
    }

    public String getTVER() {
        return process(TVER).replace("'", "");
    }

    public String getTDATETIME() {
        return TDATETIME;
    }

    public double getUCL() {
        return UCL;
    }

    public String getUNITS() {
        return process(UNITS);
    }

    public double getUSL() {
        return USL;
    }

    public int getVHMBNO() {
        return VHMBNO;
    }

    public String getVHMFNO() {
        return process(VHMFNO);
    }

    public String getVHPRNO() {
        return process(VHPRNO);
    }

    private String process(String str) {
        if (str == null || str.length() == 0) {
            return "";
        } else {
            return str.trim();
        }
    }
}
