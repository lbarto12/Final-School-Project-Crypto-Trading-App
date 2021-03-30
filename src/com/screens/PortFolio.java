package com.screens;

import com.LLayout.Component.LCanvas;
import com.LLayout.Master;
import com.company.Main;
import com.company.utility.Trade;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class PortFolio extends Master implements MouseWheelListener {
    private Main window;

    public PortFolio(Main window) {
        super(window);
        this.window = window;
        this.init();
    }

    private LCanvas canvas = new LCanvas(){
        @Override
        public void paintComponent(Graphics2D g) {
            super.paintComponent(g);

            g.setColor(Color.magenta);
            g.fillRect(0, 0, 100, 100);
        }
    };

    private void init(){
        this.add(canvas);
    }


    private double currentBalance = 10000;
    private double btcOwned = 0;
    private double totalBalance = this.currentBalance;

    private final ArrayList<Trade> trades = new ArrayList<>();

    public double getCurrentBalance(){
        return currentBalance;
    }

    public double getBtcOwned() {
        return btcOwned;
    }

    public void buy(Double value, Double currentPrice){
        if (this.currentBalance >= value){
            this.currentBalance -= value;
            this.btcOwned += (value / currentPrice);
            this.totalBalance = calculateTotalBalance(currentPrice);
            this.trades.add(new Trade(value, currentPrice, System.currentTimeMillis(), Trade.Type.Buy));
        }
        else System.out.println("insuf funds");
    }

    public void sell(Double value, Double currentPrice){
        if (this.btcOwned >= (value / currentPrice)){
            this.currentBalance += value;
            this.btcOwned -= (value / currentPrice);
            this.totalBalance = calculateTotalBalance(currentPrice);
            this.trades.add(new Trade(value, currentPrice, System.currentTimeMillis(), Trade.Type.Sell));
        }
        else System.out.println("insuf funds");
    }

    public double calculateTotalBalance(Double currentPrice){
        return this.currentBalance + (this.btcOwned * currentPrice);
    }

    public double getDollarValueOfOwned(Double currentPrice){
        return this.btcOwned * currentPrice;
    }



// Display


    private int index = 0;


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Use index to create a "scroll down" kind of thing through trades
        this.window.repaint();
    }
}
