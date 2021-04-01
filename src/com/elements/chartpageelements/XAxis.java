package com.elements.chartpageelements;

import com.LLayout.Component.LLabel;
import com.LLayout.Layouts.HorizontalLayout;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class XAxis extends HorizontalLayout {
    public XAxis(){
        this.init();
    }

    private final ArrayList<LLabel> stamps = new ArrayList<>();

    private void init(){
        this.add(new LLabel().setFillColor(Color.darkGray)); // Left Buffer
        for (int i = 0; i < 5; ++i){
            this.stamps.add(new LLabel("Wait...").setFillColor(Color.darkGray).setFontSize(12).setTextColor(Color.lightGray));
            this.add(this.stamps.get(i));
        }

        this.add(new LLabel().setFillColor(Color.darkGray)); // Right buffer
    }

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
