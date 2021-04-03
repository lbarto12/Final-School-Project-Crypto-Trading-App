package com.elements.portfolioelements;

import com.company.utility.Trade;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInfo implements Serializable {

    /**
     * Stores current balance
     */
    public double currentBalance = 10000;

    /**
     * Stores how much BTC is owned
     */
    public double btcOwned = 0;

    /**
     * Stores total balance
     */
    public double totalBalance = this.currentBalance;

    /**
     * Internal {@code ArrayList<Trade>} to store all trades that have been made
     */
    public final ArrayList<Trade> trades = new ArrayList<>();
}
