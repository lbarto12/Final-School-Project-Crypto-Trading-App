package com.elements.portfolioelements;

import com.company.utility.DataPoint;
import com.company.utility.Trade;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInfo implements Serializable {

    public double currentBalance = 10000;
    public double btcOwned = 0;
    public double totalBalance = this.currentBalance;

    public final ArrayList<Trade> trades = new ArrayList<>();
}
