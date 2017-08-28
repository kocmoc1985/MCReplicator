/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mc_lab_trell_rep;

/**
 *
 * @author KOCMOC
 */
public class TrellrepThr implements Runnable{

    private String dateFrom;
    private String dateTo;

    public TrellrepThr(String dateFrom, String dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
    
    
    
    @Override
    public void run() {
        Trellrep trellrep = new Trellrep(dateFrom,dateTo);
    }
    
}
