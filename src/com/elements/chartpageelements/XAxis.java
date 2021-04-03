package com.elements.chartpageelements;

import com.LLayout.Component.LLabel;
import com.LLayout.Layouts.HorizontalLayout;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class XAxis extends HorizontalLayout {
    /**
     * Default Constructor
     */
    public XAxis(){
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
        this.add(new LLabel().setFillColor(Color.darkGray)); // Left Buffer
        for (int i = 0; i < 5; ++i){
            this.stamps.add(new LLabel("Wait...").setFillColor(Color.darkGray).setFontSize(12).setTextColor(Color.lightGray));
            this.add(this.stamps.get(i));
        }

        this.add(new LLabel().setFillColor(Color.darkGray)); // Right buffer
    }

    /**
     * Update the data set to reflect current values
     *
     * @param data Data to copy from
     */
    public void updateLabels(ArrayList<Long> data){
        if (data.size() > 1) {
            double max = Collections.max(data);
            double min = Collections.min(data);
            double step = (max - min) / 5;

            for (int i = 0; i < 5; ++i) {
                this.stamps.get(i).setText(
                        new SimpleDateFormat("MMM dd, hh:mm").format(min + step * i)
                );
            }
        }
        else {
            for (var i: this.stamps){
                i.setText("Wait...");
            }
        }
    }
}
