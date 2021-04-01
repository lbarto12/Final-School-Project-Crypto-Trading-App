package com.company.utility;

import java.util.ArrayList;

public class DataPoint {
    public DataPoint(Double price, Long time){
        this.price = price;
        this.time = time;
        DataPoint.all.add(this);
    }

    public DataPoint(Double price, Long time, Boolean addToList){
        this.price = price;
        this.time = time;
        if (addToList)
            DataPoint.all.add(this);
    }

    public Double price;
    public Long time;

    public static ArrayList<DataPoint> all = new ArrayList<>();
}
