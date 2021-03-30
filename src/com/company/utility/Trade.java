package com.company.utility;

public class Trade {
    public Trade(Double value, Double price, Long time) {
        this.value = value;
        this.buyPrice = price;
        this.buyTime = time;
    }

    public Double value;
    public Double buyPrice;
    public Long buyTime;
}
