package com.elements.chartpageelements;

import com.LLayout.Component.LButton;
import com.LLayout.Component.LLabel;
import com.LLayout.Layouts.HorizontalLayout;
import com.LLayout.Layouts.VerticalLayout;

public class InfoPanel extends HorizontalLayout {

    /**
     * Default Constructor
     */
    public InfoPanel(){
        this.init();
    }

    /**
     * Internal {@code LLabel} to display balance in dollars
     */
    public LLabel balanceInDollars = new LLabel("Loading...");

    /**
     * Internal {@code LLabel} to display total balance in dollars
     */
    public LLabel totalBalanceDollars = new LLabel("Loading...");

    /**
     * Internal {@code LButton} to display total bitcoin owned
     */
    public LButton btcOwnedValue = new LButton("Loading...");

    /**
     * Internal {@code LLabel} to display the current time
     */
    public LLabel currentTime = new LLabel("Loading...");

    /**
     * Initialize this. Sets layout & padding
     */
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
