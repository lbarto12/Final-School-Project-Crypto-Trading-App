package com.company.utility;

import java.io.Serializable;

public class Trade implements Serializable {

    /**
     * Default Constructor
     *
     * @param value value of trade
     * @param price price of BTC at time of trade
     * @param time time of trade execution
     * @param type denotes order {@code Type}
     */
    public Trade(Double value, Double price, Long time, Type type) {
        this.value = value;
        this.buyPrice = price;
        this.buyTime = time;
        this.type = type;
    }

    /**
     * {@code Enum} for order types
     */
    public enum Type{
        Buy,
        Sell
    }

    /**
     * Internal value of trade
     */
    public Double value;

    /**
     * Internal price of BTC at time of trade
     */
    public Double buyPrice;

    /**
     * Internal time of trade execution
     */
    public Long buyTime;

    /**
     * Internal order {@code Type}
     */
    public Type type;
}
