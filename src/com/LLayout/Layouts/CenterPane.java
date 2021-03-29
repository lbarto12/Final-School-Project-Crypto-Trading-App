package com.LLayout.Layouts;

import com.LLayout.Component.LButton;
import com.LLayout.Utility.LObject;
import com.LLayout.Utility.RectL;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CenterPane extends LLayout<CenterPane> {

    private HorizontalLayout north = new HorizontalLayout();
    private HorizontalLayout south = new HorizontalLayout();
    private VerticalLayout east = new VerticalLayout();
    private VerticalLayout west = new VerticalLayout();
    private VerticalLayout center = new VerticalLayout();



    @Override
    public CenterPane add(LObject component) {
        this.add(component, CPConstraints.CENTER);
        return this;
    }

    public CenterPane add(LObject component, CPConstraints area) {
        switch (area){
            case NORTH -> this.north.add(component);
            case SOUTH -> this.south.add(component);
            case EAST -> this.east.add(component);
            case WEST -> this.west.add(component);
            case CENTER -> this.center.add(component);
        }
        return this;
    }

    public CenterPane addAll(CenterPaneElement... elements) {
        for (var i : elements)
            this.add(i.self, i.con);
        return this;
    }

    @Override
    public void updateBounds(ComponentEvent e, RectL<Integer> newBounds) {
        this.bounds = newBounds;

        // NORTH
        this.north.updateBounds(e, new RectL<>(
                this.bounds.x,
                this.bounds.y,
                this.bounds.width,
                this.bounds.height / 10
        ));

        // SOUTH
        this.south.updateBounds(e, new RectL<>(
                this.bounds.x,
                this.bounds.y + (int)(this.bounds.height * .9),
                this.bounds.width,
                this.bounds.height / 10
        ));

        // EAST
        this.east.updateBounds(e, new RectL<>(
                this.bounds.x + (int)(this.bounds.width * .9),
                this.bounds.y + this.bounds.height / 10,
                this.bounds.width / 10,
                (int)(this.bounds.height * .8)
        ));

        // WEST
        this.west.updateBounds(e, new RectL<>(
                this.bounds.x,
                this.bounds.y + this.bounds.height / 10,
                this.bounds.width / 10,
                (int)(this.bounds.height * .8)
        ));

        this.center.updateBounds(e, new RectL<>(
                this.bounds.x + this.bounds.width / 10,
                this.bounds.y + this.bounds.height / 10,
                (int)(this.bounds.width * .8),
                (int)(this.bounds.height * .8)
        ));

    }

    @Override
    public void paintComponent(Graphics2D g) {
        g.setColor(this.fillColor);
        g.fillRect(this.bounds.x,
                this.bounds.y,
                this.bounds.width,
                this.bounds.height
        );

        this.center.paintAll(g);
        this.north.paintAll(g);
        this.south.paintAll(g);
        this.east.paintAll(g);
        this.west.paintAll(g);
    }

    @Override
    public void handleRelease(MouseEvent e) {
        this.north.handleRelease(e);
        this.east.handleRelease(e);
        this.south.handleRelease(e);
        this.west.handleRelease(e);
        this.center.handleRelease(e);
    }

    @Override
    public void handleDrag(MouseEvent e){
        this.north.handleDrag(e);
        this.south.handleDrag(e);
        this.east.handleDrag(e);
        this.west.handleDrag(e);
        this.center.handleDrag(e);
    }

    @Override
    public void handleClick(MouseEvent e){
        this.center.handleClick(e);
        this.north.handleClick(e);
        this.south.handleClick(e);
        this.east.handleClick(e);
        this.west.handleClick(e);
    }

    @Override
    public void handleKeyboard(KeyEvent e) {
        this.center.handleKeyboard(e);
        this.north.handleKeyboard(e);
        this.south.handleKeyboard(e);
        this.east.handleKeyboard(e);
        this.west.handleKeyboard(e);
    }

    @Override
    public void resetButtonColors(){
        this.center.resetButtonColors();
        this.north.resetButtonColors();
        this.south.resetButtonColors();
        this.east.resetButtonColors();
        this.west.resetButtonColors();
    }
}

