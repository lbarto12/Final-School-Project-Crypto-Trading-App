package com.LLayout.Layouts;

import com.LLayout.Component.LButton;
import com.LLayout.Component.LCanvas;
import com.LLayout.Utility.RectL;

import javax.swing.*;
import java.awt.event.ComponentEvent;

public class HorizontalLayout extends LLayout<HorizontalLayout> {

//================================================ Bounds ==============================================================

    @Override
    public void updateBounds(ComponentEvent e, RectL<Integer> newBounds) {
        this.bounds = newBounds;

        for (int i = 0; i < this.allElements.size(); ++i) {
            RectL<Integer> nextSize = new RectL<>(
                    (this.bounds.x + (this.bounds.width / this.allElements.size()) * i),
                    this.bounds.y,
                    this.bounds.width / (this.allElements.size()),
                    this.bounds.height
            );

            this.allElements.get(i).updateBounds(e, nextSize);

        }
    }

//======================================================================================================================
}
