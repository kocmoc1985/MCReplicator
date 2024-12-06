/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc_to_omsk_lab;

/**
 *
 * @author KOCMOC
 */
public class Insert_Entry {

    protected final String ORDERID;
    protected final String RECIPEID;
    protected final int BATCHNR;
    protected final String STARTDATE;
    protected final int LINENR;

    public Insert_Entry(String ORDERID, String RECIPEID, int BATCHNR, String STARTDATE, int LINENR) {
        this.ORDERID = ORDERID;
        this.RECIPEID = RECIPEID;
        this.BATCHNR = BATCHNR;
        this.STARTDATE = STARTDATE;
        this.LINENR = LINENR;
    }

}
