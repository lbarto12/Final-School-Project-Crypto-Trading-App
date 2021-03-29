package com.elements.chartpageelements;

import com.LLayout.Component.LCanvas;
import com.LLayout.Utility.Vector2L;
import com.company.Main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class Chart extends LCanvas implements MouseWheelListener, MouseMotionListener, MouseListener {
    private Main window;

    public Chart(Main window){
        this.window = window;
        this.init();
    }

    private int rangeShowing = 100;
    private float minPoint = 1;
    private int limit;

    private ArrayList<Double> btcData = new ArrayList();

    private ArrayList<Double> currentData = new ArrayList();

    private void init(){
        this.window.addMouseWheelListener(this);
        this.window.addMouseMotionListener(this);
        this.window.addMouseListener(this);

        this.setFillColor(Color.BLACK);

        for (int i = 0; i < 100; ++i){
            this.currentData.add(Math.random() * 100);
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
        if(currentData.size() > 0) {

            this.limit = (int)(this.currentData.size() * (float)(this.rangeShowing / 100.f));

            if (this.currentData.size() - minPoint < limit){
                this.limit = this.currentData.size() - (int)minPoint;
            }

            double _max = Collections.max(currentData), _min = Collections.min(currentData);
            double max = _max + ((_max - _min) * .1f);
            double min = _min - ((_max - _min) * .1f);
            double xStep = (double) this.visibleBounds.width / ((double) limit);
            double yStep = (double) this.visibleBounds.height / (max - min);


            if (minPoint < 1) this.minPoint = 1;
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




// Handling Logic:
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0){
            this.rangeShowing++;
            if (minPoint > 1) minPoint--;
            else minPoint = 1;
        }
        else if (e.getWheelRotation() < 0){
            this.rangeShowing--;
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
                this.minPoint <= 100 && this.minPoint > 0
                && this.minPoint <= this.currentData.size() - this.limit
        ){
            float factor = (float)(clickPos.x - e.getX()) / (float)this.visibleBounds.width;
            float amount = limit * factor;
            this.minPoint += amount;
        }
        if (this.minPoint > this.currentData.size() - this.limit)
            this.minPoint = this.currentData.size() - this.limit;
        else if (this.minPoint < 1){
            this.minPoint = 1;
        }

        this.clickPos.x = e.getX();
        this.clickPos.y = e.getY();

        window.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    private Vector2L<Integer> clickPos = new Vector2L<>(0, 0);

    @Override
    public void mousePressed(MouseEvent e) {
        this.clickPos.x = e.getX();
        this.clickPos.y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
