package com.LLayout.Layouts;

import com.LLayout.Component.LButton;
import com.LLayout.Component.LCanvas;
import com.LLayout.Utility.RectL;

import javax.swing.*;
import java.awt.event.ComponentEvent;

public class VerticalLayout extends LLayout<VerticalLayout> {

//================================================= Bounds =============================================================

    @Override
    public void updateBounds(ComponentEvent e, RectL<Integer> newBounds) {
        this.bounds = newBounds;

        for (int i = 0; i < this.allElements.size(); ++i) {
            RectL<Integer> nextSize = new RectL<>(
                    this.bounds.x,
                    (this.bounds.y + (this.bounds.height / this.allElements.size()) * i),
                    this.bounds.width,
                    this.bounds.height / (this.allElements.size())
            );

            this.allElements.get(i).updateBounds(e, nextSize);

        }
    }

//======================================================================================================================
}
