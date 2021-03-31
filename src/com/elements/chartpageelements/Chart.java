package com.elements.chartpageelements;

import com.LLayout.Component.LCanvas;
import com.LLayout.Master;
import com.LLayout.Utility.Vector2L;
import com.company.Main;
import com.company.utility.DataFetcher;
import com.company.utility.DataPoint;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class Chart extends LCanvas implements MouseWheelListener, MouseMotionListener, MouseListener {
    private final Main window;
    private final XAxis xAxis;
    private final YAxis yAxis;

    public Chart(Main window, XAxis xAxis, YAxis yAxis){
        this.window = window;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.init();
    }

    private int rangeShowing = 100;
    private float minPoint = 1;
    private int limit;

    private final ArrayList<Double> currentData = new ArrayList<>();
    private final ArrayList<Long> currentTimes = new ArrayList<>();


    private void init(){
        this.window.addMouseWheelListener(this);
        this.window.addMouseMotionListener(this);
        this.window.addMouseListener(this);

        this.setFillColor(Color.BLACK);

        for (int i = 0; i < 100; ++i){

        }

        new DataFetcher(this,"https://robinhood.com/crypto/BTC").start();


        for (var i : Chart.loadDataPoints()){
            this.currentData.add(i.price);
            this.currentTimes.add(i.time);
        }
    }

    @Override
    public void paintComponent(Graphics2D g) {
        super.paintComponent(g);

        g.setColor(Color.darkGray);

        for (int i = 0; i < 10; ++i){
            // Vertical
            g.drawLine(
                    i * (this.visibleBounds.width / 10) + this.visibleBounds.x, this.visibleBounds.y,
                    i * (this.visibleBounds.width / 10) + this.visibleBounds.x, this.visibleBounds.y + this.visibleBounds.height
            );

            // Horizontal
            g.drawLine(
                    this.visibleBounds.x, i * (this.visibleBounds.height / 10)  + this.visibleBounds.y,
                    this.visibleBounds.x + this.visibleBounds.width, i * (this.visibleBounds.height / 10) + this.visibleBounds.y
            );
        }


        g.setColor(Color.ORANGE);
        if(currentData.size() > 1) {

            this.limit = (int)(this.currentData.size() * (this.rangeShowing / 100.f));

            if (this.currentData.size() - minPoint < limit){
                this.limit = this.currentData.size() - (int)minPoint;
            }


            if (minPoint < 1)
                this.minPoint = 1;
            else if (this.minPoint > this.currentData.size() - this.limit)
                this.minPoint = this.currentData.size() - this.limit;
            var tempData = new ArrayList<>(this.currentData.subList((int)minPoint, (int)(minPoint + limit)));
            var tempTimes = new ArrayList<>(this.currentTimes.subList((int)minPoint, (int)(minPoint + limit)));

            this.xAxis.updateLabels(tempTimes);
            this.yAxis.updateLabels(tempData);


            double _max = Collections.max(tempData), _min = Collections.min(tempData);
            double max = _max + ((_max - _min) * .1f);
            double min = _min - ((_max - _min) * .1f);
            double xStep = (double) this.visibleBounds.width / ((double) limit);
            double yStep = (double) this.visibleBounds.height / (max - min);

            for (int i = (int)minPoint; i <= minPoint + limit && i < currentData.size(); i++) {
                if (i == minPoint + limit || i == currentData.size() - 1) g.setColor(Color.GREEN);
                g.drawLine(
                        this.visibleBounds.x + (int)xStep + (int) (xStep * (i - 1 - minPoint)),
                        this.visibleBounds.y + (int) (yStep * (max - currentData.get(i - 1))),
                        this.visibleBounds.x + (int)xStep + (int) (xStep * (i - minPoint)),
                        this.visibleBounds.y + (int) (yStep * (max - currentData.get(i)))
                );
            }
        }

    }


// data
    public void addDataPoint(Double point){
        this.currentData.add(point);
        this.currentTimes.add(System.currentTimeMillis());
        if (rangeShowing != 100 && (int)(this.minPoint + this.limit + 1) == this.currentData.size()){
            minPoint++;
        }

        // Should save the point to the static arraylist, makes it easy to save to file
        new DataPoint(this.currentData.get(currentData.size() - 1), this.currentTimes.get(currentTimes.size() - 1));

        this.window.repaint();
    }

    public int getDataSize(){
        return this.currentData.size();
    }

    public Double lastDataPoint(){
        return this.currentData.get(this.currentData.size() - 1);
    }


    public static void saveDataPoints(){
        for (var i : DataPoint.all){
            // save each point in a row, remember to start writing at first blank row!
        }
    }

    public static ArrayList<DataPoint> loadDataPoints(){
        var temp = new ArrayList<DataPoint>();

        // Load points into temp and return it;

        return temp;
    }

// Handling Logic:
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0){
            this.rangeShowing++;
            if (minPoint > 1) minPoint--;
        }
        else if (e.getWheelRotation() < 0){
            this.rangeShowing--;
            if (minPoint < this.currentData.size() - 5 && this.rangeShowing > 10) minPoint++;
        }


        if (this.rangeShowing < 10)
            this.rangeShowing = 10;
        else if (this.rangeShowing > 100)
            this.rangeShowing = 100;


        this.window.repaint();
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        if (
                this.minPoint > 0
                && rangeShowing != 100
        ){
            float factor = (float)(clickPos.x - e.getX()) / (float)this.visibleBounds.width;
            float amount = limit * factor;
            this.minPoint += amount;
        }

        this.clickPos.x = e.getX();
        this.clickPos.y = e.getY();

        window.repaint();
    }



    private final Vector2L<Integer> clickPos = new Vector2L<>(0, 0);

    @Override
    public void mousePressed(MouseEvent e) {
        this.clickPos.x = e.getX();
        this.clickPos.y = e.getY();
    }



    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
