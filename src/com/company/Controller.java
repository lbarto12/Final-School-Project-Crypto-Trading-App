package com.company;

import com.LLayout.Component.LButton;
import com.LLayout.Master;
import com.screens.ChartPage;
import com.screens.PortFolio;
import com.screens.State;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

public class Controller {
    private Main window;
    private ChartPage chartPage;
    private PortFolio portFolio;
    private Master currentMaster;

    public static State state = State.CHART;

    public Controller(Main window){
        this.window = window;
        this.init();
        this.window.setVisible(true);
    }

    private void init(){
        this.chartPage = new ChartPage(this.window);
        this.chartPage.options.portfolio.setOnClick(e -> {this.swap(State.PORTFOLIO);});




        this.chartPage.options.buy.setOnClick(e -> {this.buy();});
        this.chartPage.options.sell.setOnClick(e -> {this.sell();});

        this.portFolio = new PortFolio(this.window);



        this.portFolio.backButton.setOnClick(e ->{this.swap(State.CHART);});

        this.updateBalanceLabel();
        this.createInfoThread();


        this.window.add(this.chartPage);
        this.currentMaster = this.chartPage;
    }


    private void swap(State to){
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

    private void buy(){
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

    private void sell(){
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

    private void updateBalanceLabel(){
        this.chartPage.info.balanceInDollars.setText("Balance: $" +
                String.valueOf(
                        BigDecimal.valueOf(
                                this.portFolio.getCurrentBalance()
                        ).setScale(2, RoundingMode.HALF_UP).doubleValue()
                )
        );
    }

    private  boolean showExactBtc = true;

    private void createInfoThread(){
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

    private void checkValueText(){
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
