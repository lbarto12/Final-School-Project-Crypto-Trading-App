package com.elements.chartpageelements;

import com.LLayout.Component.LLabel;
import com.LLayout.Layouts.HorizontalLayout;

import java.awt.*;
import java.util.ArrayList;

public class XAxis extends HorizontalLayout {
    public XAxis(){
        this.init();
    }

    private ArrayList<Double> times = new ArrayList<>();
    private ArrayList<LLabel> stamps = new ArrayList<>();

    private void init(){
        this.add(new LLabel().setFillColor(Color.darkGray)); // Left Buffer
        for (int i = 0; i < 10; ++i){
            this.stamps.add(new LLabel("time").setFillColor(Color.darkGray));
            this.add(this.stamps.get(i));
        }

        this.add(new LLabel().setFillColor(Color.darkGray)); // Right buffer
    }
}
