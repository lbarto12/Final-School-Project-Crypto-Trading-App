package com.company;

import com.LLayout.Master;
import com.company.utility.DataPoint;
import com.screens.ChartPage;
import com.screens.PortFolio;
import com.screens.SimulatedTrading;
import com.screens.State;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Controller {
    protected Main window;
    protected ChartPage chartPage;
    protected PortFolio portFolio;

    /**
     * Holds the currently active {@code Master}.
     */
    protected Master currentMaster;

    /**
     * Current screen the program is on
     */
    public static State state = State.CHART;


    /**
     * Default Constructor
     *
     * @param window Target window
     */
    public Controller(Main window){
        this.window = window;
        this.init();
        this.window.setVisible(true);
    }


    /**
     * Simulated Constructor
     *
     * Creates a version of the trading platform without initializing so
     * it can be overridden
     */
    public Controller(){
        this.window = new Main();
        this.window.setVisible(true);
    }


    /**
     * Sets up the controller with:
     *      {@code ChartPage}
     *      {@code Portfolio}
     * and adds them to the window
     */
    private void init(){
        // Set up chart page & associated buttons
        this.chartPage = new ChartPage(this.window);
        this.chartPage.options.portfolio.setOnClick(e -> this.swap(State.PORTFOLIO));
        this.chartPage.options.buy.setOnClick(e -> this.buy());
        this.chartPage.options.sell.setOnClick(e -> this.sell());

        // If Historical button is pressed, creates a new Simulated Controller (See 'Controller' for distinction)
        this.chartPage.options.tradeHistorical.setOnClick(e ->{
            ArrayList<DataPoint> temp = new ArrayList<>();
            for (int i = 0; i < this.chartPage.chart.currentData.size(); ++i){
                temp.add(new DataPoint(
                        this.chartPage.chart.currentData.get(i),
                        this.chartPage.chart.currentTimes.get(i),
                        false
                ));
            }
            new SimulatedTrading(temp);
        });

        // Set up portfolio & associated buttons
        this.portFolio = new PortFolio(this.window);
        this.portFolio.backButton.setOnClick(e -> this.swap(State.CHART));


        // Update info panel at top of screen with accurate information
        this.createInfoThread();


        this.window.add(this.chartPage);
        this.currentMaster = this.chartPage;
    }

    /**
     * Swaps between displaying {@code ChartPage} & {@code Portfolio} respectively
     *
     * @param to New state to swap to
     */
    protected void swap(State to){
        Controller.state = to;

        // De-activate current Master panel
        this.currentMaster.selected = false;
        this.currentMaster.setVisible(false);
        this.window.remove(currentMaster);

        // Swap component listeners to new Master Panel's
        switch (Controller.state){
            case CHART -> {
                this.currentMaster = this.chartPage;
                this.window.addMouseListener(this.chartPage.chart);
                this.window.addMouseMotionListener(this.chartPage.chart);
                this.window.addMouseWheelListener(this.chartPage.chart);
                this.window.removeMouseWheelListener(this.portFolio);
            }
            case PORTFOLIO -> {
                this.currentMaster = this.portFolio;
                this.window.removeMouseListener(this.chartPage.chart);
                this.window.removeMouseMotionListener(this.chartPage.chart);
                this.window.removeMouseWheelListener(this.chartPage.chart);
                this.window.addMouseWheelListener(this.portFolio);
            }
        }

        // Activate new Master panel
        this.currentMaster.selected = true;
        this.currentMaster.setVisible(true);
        //this.currentMaster.setFocusable(true);
        //this.currentMaster.requestFocus();

        this.window.add(this.currentMaster);
        new Thread(() -> this.currentMaster.updateBounds()).start();
    }

    /**
     * Triggers {@code PortFolio.buy()}
     */
    protected void buy(){
        try {
            this.portFolio.buy(
                    Double.valueOf(this.chartPage.options.amount.getValue()),
                    this.chartPage.chart.lastDataPoint()
            );
            this.updateBalanceLabel();
        } catch (Exception ignored){}
    }

    /**
     * Triggers {@code PortFolio.sell()}
     */
    protected void sell(){
        try {
            this.portFolio.sell(
                    Double.valueOf(this.chartPage.options.amount.getValue()),
                    this.chartPage.chart.lastDataPoint()
            );
            this.updateBalanceLabel();
        } catch (Exception ignored){}
    }

    /**
     * Update {@code this.chartPage.info.balanceInDollars} to display
     * current balance
     */
    protected void updateBalanceLabel(){
        this.chartPage.info.balanceInDollars.setText("Balance: $" +
                BigDecimal.valueOf(
                        this.portFolio.getCurrentBalance()
                ).setScale(2, RoundingMode.HALF_UP).doubleValue()
        );
    }

    /**
     * boolean to determine whether {@code this.chartPage.info.btcOwnedValue}
     * should display exact BTC investment, or $ value
     */
    protected boolean showExactBtc = true;

    /**
     * Creates a {@code Thread} that updates {@code InfoPanel}
     */
    protected void createInfoThread(){
        this.updateBalanceLabel();

        new Thread(() ->{
            // Clicking on BTC owned label swaps between amounts owned & it's cash value
            this.chartPage.info.btcOwnedValue.setOnClick(e ->{showExactBtc = !showExactBtc; this.checkValueText();});

            while (true){
                try {
                    // Get balance from portfolio & display it
                    this.chartPage.info.totalBalanceDollars.setText("Net Balance: $" +
                            BigDecimal.valueOf(
                                    this.portFolio.calculateTotalBalance(
                                            this.chartPage.chart.lastDataPoint()
                                    )
                            ).setScale(2, RoundingMode.HALF_UP).doubleValue()
                    );

                    this.checkValueText();

                    // Update current time
                    this.chartPage.info.currentTime.setText(
                            new SimpleDateFormat("MMM dd, hh:mm").format(System.currentTimeMillis())
                    );
                    Thread.sleep(1000);
                } catch (Exception ignored) {}
            }

        }).start();
    }


    /**
     * Update {@code this.chartPage.info.btcOwnedValue} based on viewing mode
     */
    protected void checkValueText(){
        String temp = (showExactBtc) ?
                String.valueOf(
                        BigDecimal.valueOf(
                                this.portFolio.getBtcOwned()
                        ).setScale(5, RoundingMode.HALF_UP).doubleValue()
                ):"$" +
                BigDecimal.valueOf(
                        this.portFolio.getDollarValueOfOwned(
                                this.chartPage.chart.lastDataPoint()
                        )
                ).setScale(2, RoundingMode.HALF_UP).doubleValue();


        this.chartPage.info.btcOwnedValue.setText("BTC Owned: " +
                temp
        );
    }
}
