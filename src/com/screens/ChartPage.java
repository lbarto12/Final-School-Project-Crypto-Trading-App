package com.screens;

import com.LLayout.Layouts.CPConstraints;
import com.LLayout.Layouts.CenterPane;
import com.LLayout.Master;
import com.LLayout.Utility.LObject;
import com.company.Main;
import com.elements.chartpageelements.*;

public class ChartPage extends Master {
    private final Main window;

    /**
     * Required for {@code this.add()}
     */
    private final CenterPane _layout = new CenterPane();

    /**
     * Internal {@code OptionsPanel}
     */
    public OptionsPanel options;

    /**
     * Internal {@code InfoPanel}
     */
    public InfoPanel info = new InfoPanel();

    /**
     * Internal {@code XAxis}
     */
    private final XAxis xAxis = new XAxis();

    /**
     * Internal {@code YAxis}
     */
    private final YAxis yAxis = new YAxis();

    /**
     * Internal {@code Chart}
     */
    public Chart chart;


    /**
     * Default Constructor
     * @param window
     */
    public ChartPage(Main window) {
        super(window);
        this.window = window;
        this.init(false);
    }

    /**
     * Simulated Constructor
     *
     * @param window Target window
     * @param simulated declare the chart as a simulated one
     */
    public ChartPage(Main window, Boolean simulated) {
        super(window);
        this.window = window;
        this.init(simulated);
    }


    /**
     * Override <i>or more accurately a substitute</i> for {@code Master.add()} to
     * accommodate {@code CenterPane} component addition
     *
     * @param component {@code LObject} to add
     * @param constraints {@code CPConstraints} to denote position on screen
     * @return this
     */
    public ChartPage add(LObject component, CPConstraints constraints) {
        this._layout.add(component, constraints);
        return this;
    }

    /**
     * Initialize this. Adds:
     *      {@code OptionsPanel},
     *      {@code InfoPanel},
     *      {@code XAxis},
     *      {@code YAxis},
     *      {@code Chart}
     * with their respective {@code CPConstraints}
     *
     * @param simulated passed in from constructor, denotes whether
     *                  to create a simulated version of this
     */
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
