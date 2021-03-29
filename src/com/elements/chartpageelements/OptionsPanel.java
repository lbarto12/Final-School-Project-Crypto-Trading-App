package com.elements.chartpageelements;

import com.LLayout.Component.LButton;
import com.LLayout.Layouts.CenterPane;
import com.LLayout.Layouts.CenterPaneElement;
import com.LLayout.Layouts.VerticalLayout;
import com.company.Controller;
import com.screens.State;

public class OptionsPanel extends VerticalLayout {
    public OptionsPanel(){
        this.init();
    }

    public LButton portfolio;
    public LButton buy;
    public LButton sell;
    public LButton indicators;

    private void init(){
        this.portfolio = new LButton("Portfolio").setPadding(2, 2);
        this.buy = new LButton("Buy").setPadding(2, 2);
        this.sell = new LButton("Sell").setPadding(2, 2);
        this.indicators = new LButton("Indicators").setPadding(2, 2);

        this.addAll(
                portfolio,
                buy,
                sell,
                indicators
        );
    }
}
