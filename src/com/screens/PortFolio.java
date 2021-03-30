package com.screens;

import com.LLayout.Master;
import com.company.Main;

public class PortFolio extends Master {
    public PortFolio(Main window) {
        super(window);
    }

    private double currentBalance = 10000;
    private double btcOwned = 0;
    private double totalBalance = this.currentBalance;

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
        }
        else System.out.println("insuf funds");
    }

    public void sell(Double value, Double currentPrice){
        if (this.btcOwned >= (value / currentPrice)){
            this.currentBalance += value;
            this.btcOwned -= (value / currentPrice);
            this.totalBalance = calculateTotalBalance(currentPrice);
        }
        else System.out.println("insuf funds");
    }

    public double calculateTotalBalance(Double currentPrice){
        return this.currentBalance + (this.btcOwned * currentPrice);
    }

    public double getDollarValueOfOwned(Double currentPrice){
        return this.btcOwned * currentPrice;
    }
}
