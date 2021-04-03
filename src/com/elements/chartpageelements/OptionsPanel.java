package com.elements.chartpageelements;

import com.LLayout.Component.LButton;
import com.LLayout.Component.LLabel;
import com.LLayout.Component.LTextField;
import com.LLayout.Layouts.VerticalLayout;

import java.awt.*;

public class OptionsPanel extends VerticalLayout {

    /**
     * Default Constructor
     */
    public OptionsPanel(){
        this.init(false);
    }

    /**
     * Simulated Constructor
     *
     * @param simulated to denote whether the object is simulated
     */
    public OptionsPanel(Boolean simulated){
        this.init(simulated);
    }

    /**
     * Internal {@code LButton} to access {@code Portfolio}
     */
    public LButton portfolio;

    /**
     * Internal {@code LButton} to submit a buy order
     */
    public LButton buy;

    /**
     * Internal {@code LButton} to submit a sell order
     */
    public LButton sell;

    /**
     * Internal {@code LButton} to access indicators
     */
    public LButton indicators;

    /**
     * Internal {@code LButton} to begins a {@code SimulatedTrading} session
     */
    public LButton tradeHistorical;

    /**
     * Internal {@code TextField} to set order amount
     */
    public LTextField amount;

    /**
     * Initialize this
     *
     * @param simulated to denote whether this object is simulated
     */
    private void init(Boolean simulated){
        this.portfolio = new LButton("Portfolio").
                setPadding(2, 2);


        this.amount =  new LTextField().setText("100").
                setPadding(2, 2, 2, 10).
                setFillColor(Color.lightGray);

        this.buy = new LButton("Buy").
                setPadding(2, 2).
                setFillColor(new Color(50, 200, 50));


        this.sell = new LButton("Sell").
                setPadding(2, 2).
                setFillColor(new Color(200, 50, 50));
        this.indicators = new LButton("Indicators").setPadding(2, 2);


        this.tradeHistorical = new LButton("Historical").
                setPadding(2, 2);

        this.addAll(
                portfolio,
                new LLabel("Trade $:").
                        setPadding(2, 10, 2, 5),
                amount,
                buy,
                sell,
                indicators
        );

        if (!simulated){
            this.add(tradeHistorical);
        }
    }
}
