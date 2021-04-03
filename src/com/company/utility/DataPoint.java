package com.company.utility;

import java.util.ArrayList;

public class DataPoint {

    /**
     * Default Constructor
     *
     * @param price Internal price point
     * @param time Internal time point
     */
    public DataPoint(Double price, Long time){
        this.price = price;
        this.time = time;
        DataPoint.all.add(this);
    }

    /**
     * Omitted {@code ArrayList} Addition Constructor
     *
     * @param price Internal price point
     * @param time Internal time point
     * @param addToList true omits adding the data point from {@code this.all}
     */
    public DataPoint(Double price, Long time, Boolean addToList){
        this.price = price;
        this.time = time;
        if (addToList)
            DataPoint.all.add(this);
    }

    /**
     * Internal price
     */
    public Double price;

    /**
     * Internal time
     */
    public Long time;

    /**
     * External {@code ArrayList<DataPoint>} to store all session-specific data points
     */
    public static ArrayList<DataPoint> all = new ArrayList<>();
}
