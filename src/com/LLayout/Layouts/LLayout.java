package com.LLayout.Layouts;

import com.LLayout.Component.LButton;
import com.LLayout.Component.LCanvas;
import com.LLayout.Utility.LObject;
import com.LLayout.Utility.RectL;
import com.LLayout.Utility.Vector2L;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class LLayout <T extends LLayout<T>> implements LObject {
    public LLayout() {}

    public LLayout(LLayout<?>  parent){
        this.bounds = parent.bounds;
    }

    public LLayout(JPanel target){
        this.bounds = new RectL<Integer>(0, 0, target.getWidth(), target.getHeight());
    }




//=============================================== Boundaries ===========================================================
    public RectL<Integer> bounds;


    // MAKE THIS WORK
    protected Vector2L<Integer>  extension = new Vector2L<>(0, 0);

    public T setExtensionFactor(Integer xFactor, Integer yFactor) {
        this.extension = new Vector2L<>(xFactor, yFactor);
        return (T)this;
    }
    // END

//======================================================================================================================



//============================================= Objects on Layout ======================================================
    public ArrayList<LObject> allElements = new ArrayList<>();

    // ADD ELEMENT TO LAYOUT
    public T add(LObject component) {
        this.allElements.add(component);
        return (T)this;
    }

    // ADD MULTIPLE ELEMENTS TO LAYOUT
    public T addAll(LObject... components) {
        for (var i : components)
            this.add(i);
        return (T)this;
    }

//======================================================================================================================



//============================================= Painting & Color =======================================================

    // FILL COLOR
    protected Color fillColor = Color.darkGray;

    // SET BACKGROUND COLOR

    public T setBackgroundColor(Color col) {
        this.fillColor = col;
        return (T)this;
    }


    public void paintComponent(Graphics2D g) {
        g.setColor(this.fillColor);
        g.fillRect(this.bounds.x,
                this.bounds.y,
                this.bounds.width,
                this.bounds.height
        );

        for (var i: this.allElements)
            i.paintAll(g);
    }

    // PAINT LAYOUT
    public void paintAll(Graphics2D g){
        this.paintComponent(g);
        for (var i : this.allElements)
            i.paintAll(g);
    }



//======================================================================================================================



//============================================= Internal Element Handling ==============================================

    public void handleDrag(MouseEvent e){
        for (var i : this.allElements)
            i.handleDrag(e);
    }

    public void handleKeyboard(KeyEvent e){
        for (var i : this.allElements)
            i.handleKeyboard(e);
    }

    @Override
    public void handleRelease(MouseEvent e) {
        for (var i : this.allElements)
            i.handleRelease(e);
    }

    // DETERMINES WHICH BUTTON IS PRESSED IF ANY

    public void handleClick(MouseEvent e){
        for(var i : this.allElements)
            i.handleClick(e);
    }

    // TRIGGERED WHEN MOUSE IS RELEASED TO RESET ALL BUTTON COLORS
    public void resetButtonColors(){

        for (var i : this.allElements){
            if (i instanceof LLayout)
                ((LLayout<?>) i).resetButtonColors();
            else if (i instanceof LButton)
                ((LButton) i).changeToNormalColor();
        }
    }

    // IF A LAYOUT IS REPLACED (SPECIFICALLY ON MASTER PANEL) THIS MERGES ALL ELEMENTS AS TO NOT DELETE THEM

    public void consume(LLayout<?> addFrom) {
        this.allElements.addAll(0, addFrom.allElements);
    }

//======================================================================================================================
}

