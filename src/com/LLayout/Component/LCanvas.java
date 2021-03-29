package com.LLayout.Component;

import java.awt.*;
import java.util.function.Consumer;

public class LCanvas extends UIComponent<LCanvas> {
    public LCanvas(){}


    // Literally just override this to draw what you want on it
    @Override
    public void paintComponent(Graphics2D g) {
        g.setColor(this.fillColor);
        g.fillRect(
                this.visibleBounds.x,
                this.visibleBounds.y,
                this.visibleBounds.width,
                this.visibleBounds.height
        );
    }
}
