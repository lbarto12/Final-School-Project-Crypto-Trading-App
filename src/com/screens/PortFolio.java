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
    private final Main window;
    private UserInfo userInfo;

    public PortFolio(Main window) {
        super(window);
        this.window = window;
        this.init();
    }

    private LCanvas canvas;
    public LButton backButton = new LButton("Back to Chart").
            setFontSize(30).
            setFillColor(Color.darkGray).
            setTextColor(Color.WHITE);

    private void init(){

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






    public double getCurrentBalance(){
        return this.userInfo.currentBalance;
    }

    public double getBtcOwned() {
        return this.userInfo.btcOwned;
    }

    public void buy(Double value, Double currentPrice){
        if (this.userInfo.currentBalance >= value){
            this.userInfo.currentBalance -= value;
            this.userInfo.btcOwned += (value / currentPrice);
            this.userInfo.totalBalance = calculateTotalBalance(currentPrice);
            this.userInfo.trades.add(new Trade(value, currentPrice, System.currentTimeMillis(), Trade.Type.Buy));
        }
        else System.out.println("insuf funds");
    }

    public void sell(Double value, Double currentPrice){
        if (this.userInfo.btcOwned >= (value / currentPrice)){
            this.userInfo.currentBalance += value;
            this.userInfo.btcOwned -= (value / currentPrice);
            this.userInfo.totalBalance = calculateTotalBalance(currentPrice);
            this.userInfo.trades.add(new Trade(value, currentPrice, System.currentTimeMillis(), Trade.Type.Sell));
        }
        else System.out.println("insuf funds");
    }

    public double calculateTotalBalance(Double currentPrice){
        return this.userInfo.currentBalance + (this.userInfo.btcOwned * currentPrice);
    }

    public double getDollarValueOfOwned(Double currentPrice){
        return this.userInfo.btcOwned * currentPrice;
    }

// Load / Save info
    public static UserInfo loadPortfolio() throws IOException, ClassNotFoundException {
        return (UserInfo) new ObjectInputStream(new FileInputStream("userInfo.bin")).readObject();
    }

    public static void savePortfolio(UserInfo userInfo) throws IOException {
        new ObjectOutputStream(new FileOutputStream("userInfo.bin")).writeObject(
                userInfo
        );
    }


// Display


    private int scrollLevel = 0;


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
