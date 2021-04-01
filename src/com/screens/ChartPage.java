package com.screens;

import com.LLayout.Layouts.CPConstraints;
import com.LLayout.Layouts.CenterPane;
import com.LLayout.Master;
import com.LLayout.Utility.LObject;
import com.company.Main;
import com.elements.chartpageelements.*;

public class ChartPage extends Master {
    private final Main window;
    private final CenterPane _layout = new CenterPane();
    public OptionsPanel options;
    public InfoPanel info = new InfoPanel();
    private final XAxis xAxis = new XAxis();
    private final YAxis yAxis = new YAxis();
    public Chart chart;

    public ChartPage(Main window) {
        super(window);
        this.window = window;
        this.init(false);
    }

    public ChartPage(Main window, Boolean simulated) {
        super(window);
        this.window = window;
        this.init(simulated);
    }

    public ChartPage add(LObject component, CPConstraints constraints) {
        this._layout.add(component, constraints);
        return this;
    }

    private void init(Boolean simulated){
        if (simulated) this.options = new OptionsPanel(true);
        else this.options = new OptionsPanel();

        this.setLayout(this._layout);
        this.chart = new Chart(this.window, this.xAxis, this.yAxis, simulated);

        this.add(options, CPConstraints.WEST);
        this.add(info, CPConstraints.NORTH);
        this.add(xAxis, CPConstraints.SOUTH);
        this.add(yAxis, CPConstraints.EAST);
        this.add(chart, CPConstraints.CENTER);
    }

}
