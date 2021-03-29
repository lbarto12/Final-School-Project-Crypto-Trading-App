package com.screens;

import com.LLayout.Layouts.CPConstraints;
import com.LLayout.Layouts.CenterPane;
import com.LLayout.Master;
import com.LLayout.Utility.LObject;
import com.company.Main;
import com.elements.chartpageelements.*;

public class ChartPage extends Master {
    private Main window;
    private final CenterPane _layout = new CenterPane();
    public OptionsPanel options = new OptionsPanel();
    public InfoPanel info = new InfoPanel();
    private XAxis xAxis = new XAxis();
    private YAxis yAxis = new YAxis();
    private Chart chart;

    public ChartPage(Main window) {
        super(window);
        this.window = window;
        this.init();
    }

    public ChartPage add(LObject component, CPConstraints constraints) {
        this._layout.add(component, constraints);
        return this;
    }

    private void init(){
        this.setLayout(this._layout);
        this.chart = new Chart(this.window);

        this.add(options, CPConstraints.WEST);
        this.add(info, CPConstraints.NORTH);
        this.add(xAxis, CPConstraints.SOUTH);
        this.add(yAxis, CPConstraints.EAST);
        this.add(chart, CPConstraints.CENTER);
    }

}
