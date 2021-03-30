package com.elements.chartpageelements;

import com.LLayout.Component.LButton;
import com.LLayout.Component.LLabel;
import com.LLayout.Layouts.HorizontalLayout;
import com.LLayout.Layouts.VerticalLayout;

public class InfoPanel extends HorizontalLayout {
    public InfoPanel(){
        this.init();
    }

    public LLabel balanceInDollars = new LLabel("Loading...");
    public LLabel totalBalanceDollars = new LLabel("Loading...");
    public LButton btcOwnedValue = new LButton("Loading...");

    public LLabel currentTime = new LLabel("Loading...");

    private void init(){
        this.addAll(
                this.balanceInDollars.setPadding(2, 2),
                this.totalBalanceDollars.setPadding(2, 2),
                this.btcOwnedValue.setPadding(2, 2),
                new HorizontalLayout().addAll(
                        new LLabel(),
                        new VerticalLayout().addAll(
                                new LLabel("Current Date").setFontSize(12),
                                this.currentTime.setFontSize(12)
                        )
                )
        );
    }
}
