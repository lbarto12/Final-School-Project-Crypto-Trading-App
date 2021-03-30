package com.company.utility;

public class Trade {
    public Trade(Double value, Double price, Long time, Type type) {
        this.value = value;
        this.buyPrice = price;
        this.buyTime = time;
        this.type = type;
    }

    public enum Type{
        Buy,
        Sell
    }

    public Double value;
    public Double buyPrice;
    public Long buyTime;
    public Type type;
}
