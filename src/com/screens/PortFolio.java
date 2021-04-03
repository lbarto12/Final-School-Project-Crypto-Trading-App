package com.screens;

import com.LLayout.Component.LButton;
import com.LLayout.Component.LCanvas;
import com.LLayout.Component.LLabel;
import com.LLayout.Layouts.HorizontalLayout;
import com.LLayout.Master;
import com.company.Main;
import com.company.utility.Trade;
import com.elements.chartpageelements.Chart;
import com.elements.portfolioelements.UserInfo;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.SimpleDateFormat;

public class PortFolio extends Master implements MouseWheelListener, Serializable {

    /**
     * Target window
     */
    private final Main window;

    /**
     * Internal {@code UserInfo} to store user data
     */
    private UserInfo userInfo;

    /**
     * Default Constructor
     *
     * @param window target window
     */
    public PortFolio(Main window) {
        super(window);
        this.window = window;
        this.init(false);
    }

    /**
     * Simulated Constructor
     *
     * @param window target window
     * @param simulated denotes whether object should be simulated
     */
    public PortFolio(Main window, Boolean simulated) {
        super(window);
        this.window = window;
        this.init(simulated);
    }

    /**
     * Internal {@code LCanvas} to draw trades on
     */
    private LCanvas canvas;

    /**
     * {@code LButton} to return to {@code Controller}'s {@code ChartPage}
     */
    public LButton backButton = new LButton("Back to Chart").
            setFontSize(30).
            setFillColor(Color.darkGray).
            setTextColor(Color.WHITE);

    /**
     * Initialize this
     *
     * Loads <i>deserializes</i> user info from userInfo.bin
     *
     * Adds window exited listener to save <i>serialize</i> {@code UserInfo} to binary file
     *
     * @param simulated denotes whether this object should be simulated
     */
    private void init(Boolean simulated){
        if (!simulated){
            try {
                this.userInfo = PortFolio.loadPortfolio();
            } catch (Exception e) {
                this.userInfo = new UserInfo();
            }

            this.window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    try {
                        PortFolio.savePortfolio(userInfo);
                        Chart.saveDataPoints();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    System.exit(0);
                }
            });
        }
        else{
            this.userInfo = new UserInfo();
        }

        this.canvas = new LCanvas(){
            @Override
            public void paintComponent(Graphics2D g) {
                //super.paintComponent(g);

                int offest = 0;
                for (int i = userInfo.trades.size() - 1; i >= 0 ; --i){



                    g.setColor((userInfo.trades.get(i).type == Trade.Type.Buy) ?
                            new Color(50, 200, 50): new Color(200, 50, 50));

                    int overlap = Math.max(
                            this.visibleBounds.y + (this.visibleBounds.height * offest) + 2 - scrollLevel,
                            2 * this.visibleBounds.height
                    );

                    g.fillRect(
                            this.visibleBounds.x + 2,
                            overlap,
                            this.visibleBounds.width - 4,
                            this.visibleBounds.height - 4
                    );

                    g.setColor(Color.BLACK);
                    g.drawRect(
                            this.visibleBounds.x + 2,
                            overlap,
                            this.visibleBounds.width - 4,
                            this.visibleBounds.height - 4
                    );

                    offest++;


                    g.setColor(Color.white);
                    g.drawString((userInfo.trades.get(i).type == Trade.Type.Buy) ? "Buy" : "Sell",
                            20, overlap + this.visibleBounds.height / 2.f
                    );

                    g.drawString(
                            "$" +
                                    userInfo.trades.get(i).value,
                            this.visibleBounds.width / 5.f, overlap + this.visibleBounds.height / 2.f
                    );

                    g.drawString(
                            new SimpleDateFormat("MMM dd, hh:mm").format(userInfo.trades.get(i).buyTime),
                            this.visibleBounds.width / 1.15f, overlap + this.visibleBounds.height / 2.f
                    );

                }

            }
        };

        this.add(backButton);
        this.add(new HorizontalLayout().addAll(
                new LLabel("Order Type"),
                new LLabel("Order Amount"),
                new LLabel(),
                new LLabel(),
                new LLabel(),
                new LLabel(),
                new LLabel("Time of Order")
        ));
        this.add(canvas);


        var temp = new LLabel(){
            @Override
            public void paintComponent(Graphics2D g) {
            }
        };

        this.addAll(temp, temp, temp, temp);
    }

    /**
     * @return current balance
     */
    public double getCurrentBalance(){
        return this.userInfo.currentBalance;
    }

    /**
     * @return BTC owned
     */
    public double getBtcOwned() {
        return this.userInfo.btcOwned;
    }

    /**
     * Submit a buy order
     *
     * @param value amount to purchase
     * @param currentPrice current BTC value
     */
    public void buy(Double value, Double currentPrice){
        if (this.userInfo.currentBalance >= value){
            this.userInfo.currentBalance -= value;
            this.userInfo.btcOwned += (value / currentPrice);
            this.userInfo.totalBalance = calculateTotalBalance(currentPrice);
            this.userInfo.trades.add(new Trade(value, currentPrice, System.currentTimeMillis(), Trade.Type.Buy));
        }
        else System.out.println("insuf funds");
    }

    /**
     * Submit a sell order
     *
     * @param value amount to sell
     * @param currentPrice current BTC value
     */
    public void sell(Double value, Double currentPrice){
        if (this.userInfo.btcOwned >= (value / currentPrice)){
            this.userInfo.currentBalance += value;
            this.userInfo.btcOwned -= (value / currentPrice);
            this.userInfo.totalBalance = calculateTotalBalance(currentPrice);
            this.userInfo.trades.add(new Trade(value, currentPrice, System.currentTimeMillis(), Trade.Type.Sell));
        }
        else System.out.println("insuf funds");
    }

    /**
     * @param currentPrice current price of BTC
     * @return total balance of all assets
     */
    public double calculateTotalBalance(Double currentPrice){
        return this.userInfo.currentBalance + (this.userInfo.btcOwned * currentPrice);
    }

    /**
     * @param currentPrice current price of BTC
     * @return dollar value of BTC owned
     */
    public double getDollarValueOfOwned(Double currentPrice){
        return this.userInfo.btcOwned * currentPrice;
    }

    /**
     * Loads <i>deserializes</i> {@code UserInfo}
     *
     * @return loaded {@code UserInfo}
     * @throws IOException if input fails
     * @throws ClassNotFoundException if class can't be found
     */
    public static UserInfo loadPortfolio() throws IOException, ClassNotFoundException {
        return (UserInfo) new ObjectInputStream(new FileInputStream("userInfo.bin")).readObject();
    }

    /**
     * Saves <i>serializes</i> {@code UserInfo}
     *
     * @param userInfo to save
     * @throws IOException if output fails
     */
    public static void savePortfolio(UserInfo userInfo) throws IOException {
        new ObjectOutputStream(new FileOutputStream("userInfo.bin")).writeObject(
                userInfo
        );
    }

    /**
     * Where the user is positioned in their {@code PortFolio}
     */
    private int scrollLevel = 0;

    /**
     * Implements {@code Interface MouseWheelListener}
     *
     * Scrolls directionally through trades list
     *
     * @param e mouse wheel event
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0){
            if (scrollLevel <= (this.userInfo.trades.size() - 1) * this.canvas.getVisibleBounds().height)
                scrollLevel += 10;
        }
        else if (e.getWheelRotation() < 0){
            if (scrollLevel >= 10)
                scrollLevel -= 10;
        }

        this.window.repaint();
    }
}
