package com.elements.chartpageelements;

import com.LLayout.Component.LLabel;
import com.LLayout.Layouts.VerticalLayout;

import java.awt.*;
import java.util.ArrayList;

public class YAxis extends VerticalLayout {
    public YAxis(){
        this.init();
    }

    private ArrayList<Double> prices = new ArrayList<>();
    private ArrayList<LLabel> stamps = new ArrayList<>();

    private void init(){
        for (int i = 0; i < 5; ++i){
            this.stamps.add(new LLabel("price").setFillColor(Color.darkGray));
            this.add(this.stamps.get(i));
        }
    }
}
