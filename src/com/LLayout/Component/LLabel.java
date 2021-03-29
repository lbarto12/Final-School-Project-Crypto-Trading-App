package com.LLayout.Component;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LLabel extends UIComponent<LLabel> {
    public LLabel() {
    }

    public LLabel(String text) {
        this.componentText = text;
    }

    //=================================================== Text =============================================================
    private String scaleSizeToText(Rectangle2D textBound) {
        boolean tooSmall = false;
        if (this.visibleBounds.width < textBound.getWidth()) {
            //this.visibleBounds.width = (int)textBound.getWidth();
            this.visibleBounds.x = this.elementBounds.x + this.elementBounds.width / 2 - this.visibleBounds.width / 2;
            tooSmall = true;
        }

        // I literally have no clue why this works...
        if (this.visibleBounds.height < textBound.getHeight() + this.visibleBounds.height / 6.f) {
            //this.visibleBounds.height = (int)textBound.getHeight();
            this.visibleBounds.y = this.elementBounds.y + this.elementBounds.height / 2 - this.visibleBounds.height / 2;
            tooSmall = true;
        }

        if (tooSmall) return "...";
        else return this.componentText;
    }

//======================================================================================================================

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

        g.setColor(this.textColor);
        g.drawString(toDisplay,
                (float) (visibleBounds.x + (visibleBounds.width / 2) - (textBound.getWidth() / 2)),
                (float) (visibleBounds.y + (visibleBounds.height / 2))
        );
    }
}

