package com.elements.chartpageelements;

import com.LLayout.Component.LButton;
import com.LLayout.Component.LLabel;
import com.LLayout.Component.LTextField;
import com.LLayout.Layouts.VerticalLayout;

import java.awt.*;

public class OptionsPanel extends VerticalLayout {
    public OptionsPanel(){
        this.init(false);
    }

    public OptionsPanel(Boolean simulated){
        this.init(simulated);
    }

    public LButton portfolio;
    public LButton buy;
    public LButton sell;
    public LButton indicators;
    public LButton tradeHistorical;

    public LTextField amount;

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
