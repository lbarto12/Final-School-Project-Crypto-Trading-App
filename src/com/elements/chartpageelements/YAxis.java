package com.elements.chartpageelements;

import com.LLayout.Component.LLabel;
import com.LLayout.Layouts.VerticalLayout;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class YAxis extends VerticalLayout {

    /**
     * Default Constructor
     */
    public YAxis(){
        this.init();
    }

    /**
     * Internal {@code ArrayList<LLabel>} to store value labels
     */
    private final ArrayList<LLabel> stamps = new ArrayList<>();

    /**
     * Initialize this. Adds labels to this
     */
    private void init(){
        for (int i = 0; i < 5; ++i){
            this.stamps.add(new LLabel("Wait...").setFillColor(Color.darkGray).setFontSize(12).setTextColor(Color.lightGray));
            this.add(this.stamps.get(i));
        }
    }

    /**
     * Update the data set to reflect current values
     *
     * @param data Data to copy from
     */
    public void updateLabels(ArrayList<Double> data){
        if (data.size() > 1) {
            double max = Collections.max(data);
            double min = Collections.min(data);
            double step = (max - min) / 5;

            for (int i = 0; i < 5; ++i) {
                this.stamps.get(i).setText("$" +
                        BigDecimal.valueOf(
                                max - (step * i)
                        ).setScale(
                                5, RoundingMode.HALF_UP
                        ).doubleValue() + " "
                );
            }
        } else {
            for (var i : this.stamps) {
                i.setText("Wait...");
            }
        }
    }
}
