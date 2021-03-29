package com.LLayout.Component;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class LTextField extends UIComponent<LTextField> {

    private boolean selected;

    public String getValue(){
        return this.componentText;
    }

    @Override
    public void handleClick(MouseEvent e) {
        this.selected =
                e.getX() >= this.visibleBounds.x + 7 &&
                e.getX() <= this.visibleBounds.x + this.visibleBounds.width + 7 &&
                e.getY() >= this.visibleBounds.y + 30
                && e.getY() <= this.visibleBounds.y + this.visibleBounds.height + 30;
    }

    public void handleKeyboard(KeyEvent e){
        if (selected){
            if (e.getKeyChar() == '\b'){
                if (this.componentText.length() > 0){
                    this.componentText = this.componentText.substring(0, this.componentText.length() - 1);
                }
            }
            else if (e.getKeyChar() == '\u001B' || e.getKeyChar() == '\n'){
                this.selected = false;
            }
            else {
                this.componentText += e.getKeyChar();
            }

        }

    }

    private String scaleSizeToText(Rectangle2D textBound){
        boolean tooSmall = false;
        if (this.visibleBounds.width < textBound.getWidth()){
            this.visibleBounds.x = this.elementBounds.x + this.elementBounds.width / 2 - this.visibleBounds.width / 2;
            tooSmall = true;
        }

        // I literally have no clue why this works...
        if (this.visibleBounds.height < textBound.getHeight() + this.visibleBounds.height / 6.f){
            this.visibleBounds.y = this.elementBounds.y + this.elementBounds.height / 2 - this.visibleBounds.height / 2;
            tooSmall = true;
        }

        if (tooSmall) return "...";
        else return this.componentText;
    }

    @Override
    public void paintComponent(Graphics2D g) {
        g.setFont(this.font);
        Rectangle2D textBound = g.getFontMetrics().getStringBounds(this.componentText, g);

        String toDisplay = this.scaleSizeToText(textBound);

        if (toDisplay.equals("..."))
            textBound = g.getFontMetrics().getStringBounds(toDisplay, g);

        g.setColor(this.fillColor);
        g.fillRect(
                this.visibleBounds.x,
                this.visibleBounds.y,
                this.visibleBounds.width,
                this.visibleBounds.height
        );

        if (this.selected){
            g.setColor(Color.white);
            g.fillRect(
                    this.visibleBounds.x + this.visibleBounds.width / 10,
                    this.visibleBounds.y + this.visibleBounds.height / 10,
                    this.visibleBounds.width - this.visibleBounds.width / 5,
                    this.visibleBounds.height - + this.visibleBounds.height / 5
            );
        }

        String ifSel = (this.selected) ? toDisplay + '|' : toDisplay;

        g.setColor(this.textColor);
        g.drawString(ifSel,
                (float) (visibleBounds.x + (visibleBounds.width / 2) - (textBound.getWidth() / 2)),
                (float) (visibleBounds.y + (visibleBounds.height / 2))
        );

    }

}
