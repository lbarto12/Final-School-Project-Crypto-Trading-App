package com.company;

import com.LLayout.Component.LButton;
import com.LLayout.Master;
import com.company.utility.DataPoint;
import com.screens.ChartPage;
import com.screens.PortFolio;
import com.screens.SimulatedTrading;
import com.screens.State;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Controller {
    protected Main window;
    protected ChartPage chartPage;
    protected PortFolio portFolio;
    protected Master currentMaster;

    public static State state = State.CHART;

    public Controller(Main window){
        this.window = window;
        this.init();
        this.window.setVisible(true);
    }

    public Controller(boolean simulated){
        this.window = new Main();
        this.window.setVisible(true);
    }

    private void init(){
        this.chartPage = new ChartPage(this.window);
        this.chartPage.options.portfolio.setOnClick(e -> {this.swap(State.PORTFOLIO);});




        this.chartPage.options.buy.setOnClick(e -> {this.buy();});
        this.chartPage.options.sell.setOnClick(e -> {this.sell();});

        this.portFolio = new PortFolio(this.window);



        this.portFolio.backButton.setOnClick(e ->{this.swap(State.CHART);});

        this.chartPage.options.tradeHistorical.setOnClick(e ->{
            ArrayList<DataPoint> temp = new ArrayList<>();
            for (int i = 0; i < this.chartPage.chart.currentData.size(); ++i){
                temp.add(new DataPoint(
                        this.chartPage.chart.currentData.get(i),
                        this.chartPage.chart.currentTimes.get(i),
                        false
                ));
            }
            new SimulatedTrading(temp);
        });

        this.updateBalanceLabel();
        this.createInfoThread();


        this.window.add(this.chartPage);
        this.currentMaster = this.chartPage;
    }



    protected void swap(State to){
        Controller.state = to;

        this.currentMaster.selected = false;
        this.currentMaster.setVisible(false);
        this.window.remove(currentMaster);


        switch (Controller.state){
            case CHART -> {
                this.currentMaster = this.chartPage;
                this.window.addMouseListener(this.chartPage.chart);
                this.window.addMouseMotionListener(this.chartPage.chart);
                this.window.addMouseWheelListener(this.chartPage.chart);
                this.window.removeMouseWheelListener(this.portFolio);
            }
            case PORTFOLIO -> {
                this.currentMaster = this.portFolio;
                this.window.removeMouseListener(this.chartPage.chart);
                this.window.removeMouseMotionListener(this.chartPage.chart);
                this.window.removeMouseWheelListener(this.chartPage.chart);
                this.window.addMouseWheelListener(this.portFolio);
            }
        }

        this.currentMaster.selected = true;
        this.currentMaster.setVisible(true);
        //this.currentMaster.setFocusable(true);
        //this.currentMaster.requestFocus();

        this.window.add(this.currentMaster);
        new Thread(() -> {this.currentMaster.updateBounds();}).start();
    }

    protected void buy(){
        try {
            this.portFolio.buy(
                    Double.valueOf(this.chartPage.options.amount.getValue()),
                    this.chartPage.chart.lastDataPoint()
            );
            this.updateBalanceLabel();
        } catch (Exception ex){
            System.out.println("invalid input");
        }
    }

    protected void sell(){
        try {
            this.portFolio.sell(
                    Double.valueOf(this.chartPage.options.amount.getValue()),
                    this.chartPage.chart.lastDataPoint()
            );
            this.updateBalanceLabel();
        } catch (Exception ex){
            System.out.println("invalid input");
        }
    }

    protected void updateBalanceLabel(){
        this.chartPage.info.balanceInDollars.setText("Balance: $" +
                String.valueOf(
                        BigDecimal.valueOf(
                                this.portFolio.getCurrentBalance()
                        ).setScale(2, RoundingMode.HALF_UP).doubleValue()
                )
        );
    }

    private  boolean showExactBtc = true;

    protected void createInfoThread(){
        new Thread(() ->{
            this.chartPage.info.btcOwnedValue.setOnClick(e ->{showExactBtc = !showExactBtc; this.checkValueText();});

            while (true){
                try {
                    this.chartPage.info.totalBalanceDollars.setText("Net Balance: $" +
                            String.valueOf(
                                    BigDecimal.valueOf(
                                            this.portFolio.calculateTotalBalance(
                                                    this.chartPage.chart.lastDataPoint()
                                            )
                                    ).setScale(2, RoundingMode.HALF_UP).doubleValue()
                            )
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

    protected void checkValueText(){
        String temp = (showExactBtc) ?
                String.valueOf(
                        BigDecimal.valueOf(
                                this.portFolio.getBtcOwned()
                        ).setScale(5, RoundingMode.HALF_UP).doubleValue()
                ):"$" +
                String.valueOf(
                        BigDecimal.valueOf(
                                this.portFolio.getDollarValueOfOwned(
                                        this.chartPage.chart.lastDataPoint()
                                )
                        ).setScale(2, RoundingMode.HALF_UP).doubleValue()
                );


        this.chartPage.info.btcOwnedValue.setText("BTC Owned: " +
                temp
        );
    }



}
