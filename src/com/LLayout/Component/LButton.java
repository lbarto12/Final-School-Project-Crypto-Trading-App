package com.LLayout.Component;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class LButton extends UIComponent<LButton> {
    public LButton(String text) { this.componentText = text;}
    public LButton(){ }

//================================================ Button Functionality ================================================
    @Override
    protected void elementSpecificOnClickAction(MouseEvent e) {
        this.changeToPressedColor();
    }

//======================================================================================================================



//=================================================== Bounds ===========================================================
    public LButton setPadding(int horizontal, int vertical){
        super.setPadding(horizontal,vertical);
        return this;
    }

//======================================================================================================================



//================================================== Color =============================================================
    private Color pressedColor = Color.lightGray;
    private Color currentStateColor = this.fillColor;

    public void changeToPressedColor(){
        this.currentStateColor = this.pressedColor;
    }

    public void changeToNormalColor(){
        this.currentStateColor = this.fillColor;
    }

    public LButton setPressedColor(Color color){
        this.pressedColor = color;
        return this;
    }

    @Override
    public LButton setFillColor(Color color) {
        this.currentStateColor = color;
        return super.setFillColor(color);
    }

//======================================================================================================================



//=================================================== Text =============================================================
    private String scaleSizeToText(Rectangle2D textBound){
        boolean tooSmall = false;
        if (this.visibleBounds.width < textBound.getWidth()){
            //this.visibleBounds.width = (int)textBound.getWidth();
            this.visibleBounds.x = this.elementBounds.x + this.elementBounds.width / 2 - this.visibleBounds.width / 2;
            tooSmall = true;
        }

        // I literally have no clue why this works...
        if (this.visibleBounds.height < textBound.getHeight() + this.visibleBounds.height / 6.f){
            //this.visibleBounds.height = (int)textBound.getHeight();
            this.visibleBounds.y = this.elementBounds.y + this.elementBounds.height / 2 - this.visibleBounds.height / 2;
            tooSmall = true;
        }

        if (tooSmall) return "...";
        else return this.componentText;
    }

//======================================================================================================================



//===================================================== Painting =======================================================
    @Override
    public void paintComponent(Graphics2D g) {

        g.setFont(this.font);
        Rectangle2D textBound = g.getFontMetrics().getStringBounds(this.componentText, g);

        String toDisplay = this.scaleSizeToText(textBound);

        if (toDisplay.equals("..."))
            textBound = g.getFontMetrics().getStringBounds(toDisplay, g);

        g.setColor(this.currentStateColor);
        g.fillRect(
                this.visibleBounds.x,
                this.visibleBounds.y,
                this.visibleBounds.width,
                this.visibleBounds.height
        );

        g.setColor(this.textColor);
        g.drawString(toDisplay,
                (float) (visibleBounds.x + (visibleBounds.width / 2) - (textBound.getWidth() / 2)),
                (float) (visibleBounds.y + (visibleBounds.height / 2))
        );
    }

//======================================================================================================================


}
