package com.LLayout.Component;

import com.LLayout.Utility.CircleL;
import com.LLayout.Utility.RectL;
import com.LLayout.Utility.Vector2L;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class LSlider extends UIComponent<LSlider> {
    public LSlider(){
        this.ball.setRadius(10);
        this.value = range.x;
    }

    public LSlider(Vector2L<Integer> range){
        this.ball.setRadius(10);
        this.range = range;
        this.value = range.x;
    }

    private Vector2L<Integer> range = new Vector2L<>(1, 100);
    private Color ballColor = Color.LIGHT_GRAY;
    private final CircleL ball = new CircleL(10);
    public boolean selected = false;
    private boolean setUp = true;
    private int value;

    public boolean isBeingDragged(){
        return this.selected;
    }

    public LSlider setBallRadius(int r){
        this.ball.setRadius(r);
        return this;
    }

    public LSlider setBallColor(Color color){
        this.ballColor = color;
        return this;
    }

    public LSlider setRange(Vector2L<Integer> range){
        this.range = range;
        this.value = range.x;
        return this;
    }

    public Vector2L<Integer> getRange(){
        return this.range;
    }

    public int getValue(){
        return this.value;
    }

    private void assignValue(){
        this.value = (int)(
                (
                        (this.ball.getCenter().x - (this.visibleBounds.x + (float)this.visibleBounds.width / 8)) /
                                (this.visibleBounds.x + (this.visibleBounds.width - (float)this.visibleBounds.width / 8) -
                                        (this.visibleBounds.x + (float)this.visibleBounds.width / 8))
                ) * (this.range.y - this.range.x) + this.range.x);
    }

    @Override
    public void handleRelease(MouseEvent e) {
        this.selected = false;
    }

    @Override
    public void handleDrag(MouseEvent e) {
        if (this.selected){
            if (e.getX() > (this.visibleBounds.x + this.visibleBounds.width / 8) + 7 &&
                e.getX() < this.visibleBounds.x + this.visibleBounds.width - this.visibleBounds.width / 8 + 7){
                this.ball.setCenterPosition(new Vector2L<>(
                        e.getX() - 7,
                        this.ball.getCenter().y
                ));
                this.assignValue();
            }
        }
    }

    @Override
    protected void elementSpecificOnClickAction(MouseEvent e) {
        if (this.ball.contains(new Vector2L<>(e.getX() - 7, e.getY() - 30))){
            this.selected = true;
        }
    }

    public void updateBounds(ComponentEvent e, RectL<Integer> newSize) {
        this.ball.setCenterPosition(new Vector2L<>(
                this.getScaling(),
                this.visibleBounds.y + this.visibleBounds.height / 2
        ));

        this.elementBounds = newSize;
        this.visibleBounds = new RectL<>(
                this.elementBounds.x + this.padding.left,
                this.elementBounds.y + this.padding.top,
                this.elementBounds.width - this.padding.right * 2,
                this.elementBounds.height - this.padding.bottom * 2
        );
    }


    private int getScaling(){
        int l = this.visibleBounds.x + this.visibleBounds.width / 8;
        int r = this.visibleBounds.x + (this.visibleBounds.width - this.visibleBounds.width / 8);
        float step = (float)(r - l) / (this.range.y - this.range.x);
        return (int) (step * (this.value) - ((this.range.x -1 ) * step) ) + (this.visibleBounds.x + this.visibleBounds.width / 8);
    }

    @Override
    public void paintComponent(Graphics2D g) {

        if (setUp){
            this.ball.setCenterPosition(new Vector2L<>(
                    (this.visibleBounds.x + this.visibleBounds.width / 8),
                    (this.visibleBounds.y + this.visibleBounds.height / 2)
            ));
            setUp = false;
        }
        else {
            this.ball.setCenterPosition(new Vector2L<>(
                    this.getScaling(),
                    (this.visibleBounds.y + this.visibleBounds.height / 2)
            ));
        }

        g.setColor(this.fillColor);
        g.fillRect(
                this.visibleBounds.x,
                this.visibleBounds.y,
                this.visibleBounds.width,
                this.visibleBounds.height
        );

        g.setColor(Color.darkGray);
        g.drawLine(
                this.visibleBounds.x + this.visibleBounds.width / 8,
                this.visibleBounds.y + this.visibleBounds.height / 2,
                this.visibleBounds.x + this.visibleBounds.width - this.visibleBounds.width / 8,
                this.visibleBounds.y + this.visibleBounds.height / 2
        );

        g.setColor(this.ballColor);
        g.fillOval(
                this.ball.getCenter().x - this.ball.getRadius(),
                this.ball.getCenter().y - this.ball.getRadius(),
                this.ball.getBounds().width,
                this.ball.getBounds().height

        );

    }
}
