package com.LLayout;

import com.LLayout.Layouts.LLayout;
import com.LLayout.Layouts.VerticalLayout;
import com.LLayout.Utility.LObject;
import com.LLayout.Utility.RectL;
import com.company.Controller;
import com.company.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Master extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    public Master(Main window){
        window.addMouseListener(this);
        window.addMouseMotionListener(this);
        window.addKeyListener(this);

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                layout.updateBounds(e, new RectL<>(0, 0, getWidth(), getHeight()));
                repaint();
            }
        });

        //this.setFocusable(true);
        //this.requestFocus();
    }

    public void updateBounds() {
        var e = new ComponentEvent(this, 0);
        layout.updateBounds(e, new RectL<>(0, 0, getWidth(), getHeight()));
        this.repaint();
    }


    public Master setLayout(LLayout<?> layout){
        if (this.layout != null)
            layout.consume(this.layout);
        this.layout = layout;
        return this;
    }
    private LLayout<?>  layout = new VerticalLayout();


    public Master add(LObject component){
        this.layout.add((LObject) component); return this;
    }

    public Master addAll(LObject... components){
        this.layout.addAll(components); return this;
    }

    public LLayout<?> getMasterLayout(){
        return this.layout;
    }


    @Override
    protected void paintComponent(Graphics g) {
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        this.layout.paintAll((Graphics2D)g);
    }


    public boolean selected = true;
    public static boolean mouseHeld = false;

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.selected && !Master.mouseHeld){
            this.layout.handleClick(e);
            this.repaint();
            mouseHeld = true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) {
        if (this.selected){
            layout.handleRelease(e);
            layout.resetButtonColors();
            this.repaint();
        }
            Master.mouseHeld = false;
    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.selected){
            layout.handleDrag(e);
            this.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (this.selected){
            layout.handleKeyboard(e);
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
