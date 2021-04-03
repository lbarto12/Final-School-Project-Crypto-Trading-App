package com.screens;

import com.LLayout.Component.LButton;
import com.LLayout.Component.LLabel;
import com.LLayout.Component.LSlider;
import com.LLayout.Component.LTextField;
import com.LLayout.Utility.Vector2L;
import com.company.Controller;
import com.company.utility.DataPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SimulatedTrading extends Controller{

    /**
     * Internal {@code ArrayList<DataPoint>} to store data points
     */
    private final ArrayList<DataPoint> points;

    /**
     * Default Constructor
     *
     * @param points data points to simulate
     */
    public SimulatedTrading(ArrayList<DataPoint> points) {
        super();
        this.points = points;
        this.init();
    }


    /**
     * Internal {@code LSlider} to denote tick speed
     */
    private final LSlider delaySlider = new LSlider(new Vector2L<>(1, 1000)).
            setPadding(2, 2).
            setBallRadius(7);

    /**
     * Internal {@code LTextField} to denote at what index to start the simulation
     */
    private final LTextField startIndex = new LTextField().
            setText("0").
            setPadding(2, 2).
            setFillColor(Color.lightGray);

    /**
     * Initialize this
     *
     * Does the same thing as {@code Controller} without the saving and loading.
     *
     * Adds various {@code SimulatedTrading} specific {@code UIComponent}s
     */
    private void init(){
        this.window.setTitle("Simulated");
        this.window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.chartPage = new ChartPage(this.window, true);
        this.chartPage.options.portfolio.setOnClick(e -> this.swap(State.PORTFOLIO));

        this.chartPage.options.amount.setPadding(2, 0);

        this.chartPage.options.add(new LLabel().setFillColor(Color.darkGray));

        this.chartPage.options.add(new LButton("Start Index").setPadding(2, 2).setOnClick(
                e ->{
                    if (!this.runAppendThread &&
                            Integer.parseInt(this.startIndex.getValue()) >= 0 &&
                            Integer.parseInt(this.startIndex.getValue()) < this.chartPage.chart.currentData.size() - 1
                    ){
                        this.beginFromIndex(Integer.parseInt(this.startIndex.getValue()));
                        this.runAppendThread = true;
                    }
                }
        ));
        this.chartPage.options.add(this.startIndex);

        this.chartPage.options.add(new LLabel().setFillColor(Color.darkGray));
        this.chartPage.options.add(new LLabel("Delay").setPadding(2, 2));
        this.chartPage.options.add(this.delaySlider);

        this.chartPage.chart.currentData = new ArrayList<>();
        this.chartPage.chart.currentTimes = new ArrayList<>();
        for (var i : this.points){
            this.chartPage.chart.currentData.add(i.price);
            this.chartPage.chart.currentTimes.add(i.time);
        }


        this.chartPage.options.buy.setOnClick(e -> this.buy());
        this.chartPage.options.sell.setOnClick(e -> this.sell());

        this.portFolio = new PortFolio(this.window, true);



        this.portFolio.backButton.setOnClick(e -> this.swap(State.CHART));


        this.updateBalanceLabel();
        this.createInfoThread();


        this.window.add(this.chartPage);
        this.currentMaster = this.chartPage;
    }

    /**
     * Internal {@code ArrayList<Double>} to store all prices
     */
    private ArrayList<Double> savePrices;

    /**
     * Internal {@code ArrayList<Long>} to store all times
     */
    private ArrayList<Long> saveTimes;

    /**
     * boolean to determine whether data point addition {@code Thread} is running
     */
    private boolean runAppendThread = false;

    /**
     * Index to start simulation from
     */
    private int runningIndex = 0;

    /**
     * Begins simulation from index
     *
     * @param index index to begin from
     */
    private void beginFromIndex(int index){
        this.savePrices = new ArrayList<>(this.chartPage.chart.currentData);
        this.saveTimes = new ArrayList<>(this.chartPage.chart.currentTimes);

        this.chartPage.chart.currentData = new ArrayList<>(
                this.chartPage.chart.currentData.subList(0, index)
        );
        this.chartPage.chart.currentTimes = new ArrayList<>(
                this.chartPage.chart.currentTimes.subList(0, index)
        );


        new Thread(() -> {
            var delay = this.delaySlider.getValue();

            this.runningIndex = index + 1;
            while (this.runAppendThread && runningIndex < this.saveTimes.size()){
                this.chartPage.chart.currentData.add(this.savePrices.get(runningIndex));
                this.chartPage.chart.currentTimes.add(this.saveTimes.get(runningIndex));
                runningIndex++;


                if (this.delaySlider.selected)
                    delay = delaySlider.getValue();

                if (delay < 1) delay = 1;


                try {
                    Thread.sleep(this.delaySlider.getValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.window.repaint();
            }

            this.runAppendThread = false;

        }).start();
    }

    /**
     * boolean to determine whether this simulation is running
     */
    private boolean runningSimulation = true;

    /**
     * Overrides {@code Controller} method to destroy it if the simulation is over
     */
    @Override
    protected void createInfoThread(){
        this.window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                runningSimulation = false;
            }
        });

        new Thread(() ->{
            this.chartPage.info.btcOwnedValue.setOnClick(e ->{showExactBtc = !showExactBtc; this.checkValueText();});

            while (this.runningSimulation){
                try {
                    this.chartPage.info.totalBalanceDollars.setText("Net Balance: $" +
                            BigDecimal.valueOf(
                                    this.portFolio.calculateTotalBalance(
                                            this.chartPage.chart.lastDataPoint()
                                    )
                            ).setScale(2, RoundingMode.HALF_UP).doubleValue()
                    );

                    this.checkValueText();

                    this.chartPage.info.currentTime.setText(
                            new SimpleDateFormat("MMM dd, hh:mm").format(System.currentTimeMillis())
                    );
                    Thread.sleep(1000);
                } catch (Exception ignored) {}
            }
        }).start();

    }


}
