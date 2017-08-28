/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Supplementary;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;

/**
 * Is a super class for the spedometer like graphs
 *
 * @author Administrator
 */
public class AddToBufferSpeedGraph implements Runnable {

    protected final static String CREATE_METHOD_NAME = "createSpeedGraph";
    protected DefaultValueDataset dataset = null;
    protected MeterPlot plot = null;
    protected JFreeChart chart = null;
    private ChartPanel mypan = null;
    protected String UNITS = "";
    protected int MIN_LIM = 0;
    protected int MAX_LIM = 0;
    protected int METER_ANGLE = 0;
    protected static Color CHART_PLOT_COLOR = new Color(240, 240, 240);
    protected String TITLE = "";
    protected int MAIN_VALUE_FONT_SIZE = 24;
    protected Font TITLE_FONT = JFreeChart.DEFAULT_TITLE_FONT;
    protected Font VALUE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, MAIN_VALUE_FONT_SIZE);
    protected double SCALING_COEFF = 0;
    //====
    protected boolean run = true;
    protected LinkedList<Double> BUFFER = new LinkedList();

    public AddToBufferSpeedGraph(String title, String units, Color modul_color, Font title_font) {
        if (modul_color != null) {
            CHART_PLOT_COLOR = modul_color;
        }
        if (title_font != null) {
            TITLE_FONT = title_font;
        }


        TITLE = title;
        UNITS = units;

        init();
    }

    private void init() {
        defineHeadVariables();
        buildGraph();
        startThread("MySpeedGraph-Thr");
    }

    protected void defineHeadVariables() {
        defineScalingCoeff();
        METER_ANGLE = 190;
        MIN_LIM = 0;
        MAX_LIM = 360;
    }

    /**
     * This is for speed graph only other sub classes should owerite this one
     */
    protected void defineScalingCoeff() {
        SCALING_COEFF = 1;
    }

    protected final void startThread(String threadName) {
        Thread thisThr = new Thread(this);
        thisThr.setName(threadName);
        thisThr.start();
    }

    /**
     * Set actual value
     *
     * @param value
     */
    protected void go(double value) {
        double val = value / SCALING_COEFF;
        dataset.setValue(val);
        //=================================
        if (val > MAX_LIM) {
            MAX_LIM = (int) val + 10;
            adjustApereance();
        }
    }

    public synchronized void go(Double value) {
        BUFFER.add(value);
        notify();
    }

    public String getOwnerClassName() {
        return this.getClass().getName();
    }

    /**
     * Provides with the graph instance to be added to the container
     *
     * @return
     */
    public Component getGraph() {
        return this.mypan;
    }

    protected final void buildGraph() {
        dataset = new DefaultValueDataset(0);
        plot = new MeterPlot(dataset);

        chart = new JFreeChart(TITLE, // chart title
                TITLE_FONT,
                plot, // plot
                true);

        adjustApereance();

        //make borders visible
        chart.setBorderVisible(true);
        chart.setBorderPaint(Color.BLACK);

        mypan = new ChartPanel(chart);

    }

    /**
     *
     */
    protected void adjustApereance() {

        plot.setRange(new Range(MIN_LIM, MAX_LIM));

        //Set the color of the speedometer
        plot.setDialBackgroundPaint(Color.BLACK);

        //Set color of the 'strelka'
        plot.setNeedlePaint(Color.YELLOW);

        //set color of the value
        plot.setValuePaint(Color.WHITE);

        //
        plot.setUnits(UNITS);

        //Font of the value
        plot.setValueFont(VALUE_FONT);

        //Color of the small lines corresponding to speed
        plot.setTickPaint(Color.GREEN);

        plot.setMeterAngle(METER_ANGLE);

        //Set Scaling
        plot.setTickSize(60);

        //Set the background color

        chart.setBackgroundPaint(CHART_PLOT_COLOR);

    }

    public synchronized void stop() {
        run = false;
        notify();
    }

    @Override
    public void run() {
        while (run) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AddToBufferSpeedGraph.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (BUFFER.isEmpty() == false) {
                double value = BUFFER.poll();
                go(value);
            }
        }
    }

    public String getTitle() {
        return this.TITLE;
    }

    public String getPropertyNameOfValue() {
        return "speed_mixer";
    }

    public double getCoeff() {
        return SCALING_COEFF;
    }

    public String getCreateMethodName() {
        return CREATE_METHOD_NAME;
    }
}
