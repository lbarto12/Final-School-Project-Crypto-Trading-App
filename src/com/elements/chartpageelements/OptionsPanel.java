package com.elements.chartpageelements;

import com.LLayout.Component.LButton;
import com.LLayout.Component.LLabel;
import com.LLayout.Component.LTextField;
import com.LLayout.Layouts.CenterPane;
import com.LLayout.Layouts.CenterPaneElement;
import com.LLayout.Layouts.HorizontalLayout;
import com.LLayout.Layouts.VerticalLayout;
import com.company.Controller;
import com.screens.State;

import java.awt.*;

public class OptionsPanel extends VerticalLayout {
    public OptionsPanel(){
        this.init();
    }

    public LButton portfolio;
    public LButton buy;
    public LButton sell;
    public LButton indicators;

    public LTextField amount;

    private void init(){
        this.portfolio = new LButton("Portfolio").
                setPadding(2, 2);


        this.amount =  new LTextField().setText("100").
                setPadding(2, 2, 2, 10).
                setFillColor(Color.gray);

        this.buy = new LButton("Buy").
                setPadding(2, 2).
                setFillColor(new Color(50, 200, 50));


        this.sell = new LButton("Sell").
                setPadding(2, 2).
                setFillColor(new Color(200, 50, 50));
        this.indicators = new LButton("Indicators").setPadding(2, 2);

        this.addAll(
                portfolio,
                new LLabel("Trade $:").
                        setPadding(2, 10, 2, 5).
                        setFillColor(new Color(171, 171, 171)),
                amount,
                buy,
                sell,
                indicators
        );
    }
}
