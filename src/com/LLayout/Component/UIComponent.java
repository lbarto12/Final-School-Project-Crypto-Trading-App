package com.LLayout.Component;

import com.LLayout.Utility.LObject;
import com.LLayout.Utility.PaddingL;
import com.LLayout.Utility.RectL;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public  abstract  class  UIComponent <T extends UIComponent<T>> implements LObject {
    public UIComponent(){  }

//=================================================== Bounds ===========================================================
    protected RectL<Integer> elementBounds = new RectL<>(0, 0, 100, 50);
    protected RectL<Integer> visibleBounds = elementBounds;
    protected PaddingL padding = new PaddingL(0, 0, 0, 0);

    public RectL<Integer> getElementBounds() {
        return elementBounds;
    }

    public RectL<Integer> getVisibleBounds() {
        return visibleBounds;
    }

    public T setPadding(int horizontal, int vertical) {
        this.padding.left = horizontal;
        this.padding.right = horizontal;
        this.padding.top = vertical;
        this.padding.bottom = vertical;
        return (T)this;
    }


    public T setPadding(int left, int top, int right, int bottom) {
        this.padding.left = left;
        this.padding.right = right;
        this.padding.top = top;
        this.padding.bottom = bottom;
        return (T)this;
    }


    public void updateBounds(ComponentEvent e, RectL<Integer> newSize) {
        this.elementBounds = newSize;
        this.visibleBounds = new RectL<Integer>(
                this.elementBounds.x + this.padding.left,
                this.elementBounds.y + this.padding.top,
                this.elementBounds.width - this.padding.right * 2,
                this.elementBounds.height - this.padding.bottom * 2
        );
    }


//======================================================================================================================



//=================================================== Color & Text =====================================================
    protected Color fillColor = Color.GRAY;
    protected Color textColor = Color.black;
    protected Font font = new Font("TimesRoman", Font.PLAIN, 15);
    protected String componentText = "";

    public T setText(String text){ this.componentText = text; return (T)this; }

    public T setTextColor(Color color){  this.textColor = color; return (T)this; }

    public T setFontSize(int size){ this.font = new Font("TimesRoman", Font.PLAIN, size); return (T)this; }

    public T setFillColor(Color color){ this.fillColor = color; return (T)this; }

    @Override
    public void paintAll(Graphics2D g) {
        this.paintComponent(g);
    }

//======================================================================================================================



//========================================== Generic Functionality =====================================================
    // CLICK

    protected Consumer<MouseEvent> onClick = null;

    public T setOnClick(Consumer<MouseEvent> action){ this.onClick = action; return (T)this; }


    public void handleClick(MouseEvent e){
        if (e.getX() >= this.visibleBounds.x + 7 &&
                e.getX() <= this.visibleBounds.x + this.visibleBounds.width + 7 &&
                e.getY() >= this.visibleBounds.y + 30
                && e.getY() <= this.visibleBounds.y + this.visibleBounds.height + 30){
            this.elementSpecificOnClickAction(e);
            if (this.onClick != null) {
                this.onClick.accept(e);
            }
        }
    }

//======================================================================================================================



//=================================================== Abstract Methods =================================================

    // Override in subclass for specific action. I.e color change when a button is pressed
    protected void elementSpecificOnClickAction(MouseEvent e) {};

//======================================================================================================================
}
