package com.company;

import com.LLayout.Component.LButton;
import com.LLayout.Master;
import com.screens.ChartPage;
import com.screens.PortFolio;
import com.screens.State;

public class Controller {
    private Main window;
    private ChartPage chartPage;
    private PortFolio portFolio;
    private Master currentMaster;

    public static State state = State.CHART;

    public Controller(Main window){
        this.window = window;
        this.init();
        this.window.setVisible(true);
    }

    private void init(){
        this.chartPage = new ChartPage(this.window);
        this.chartPage.options.portfolio.setOnClick(e -> {this.swap(State.PORTFOLIO);});

        this.portFolio = new PortFolio(window);



        this.window.add(this.chartPage);
        this.currentMaster = this.chartPage;
    }


    private void swap(State to){
        Controller.state = to;

        this.currentMaster.selected = false;
        this.currentMaster.setVisible(false);
        this.window.remove(currentMaster);

        switch (Controller.state){
            case CHART -> {this.currentMaster = this.chartPage;}
            case PORTFOLIO -> {this.currentMaster = this.portFolio;}
        }

        this.currentMaster.selected = true;
        this.currentMaster.setVisible(true);

        this.window.add(this.currentMaster);
        new Thread(() -> {this.currentMaster.updateBounds();}).start();
    }
}
