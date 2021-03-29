package com.LLayout.Utility;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface LObject {
   void paintComponent(Graphics2D g);
   void handleClick(MouseEvent e);
   void paintAll(Graphics2D g);
   void updateBounds(ComponentEvent e, RectL<Integer> nextSize);
   default void handleDrag(MouseEvent e){}
   default void handleRelease(MouseEvent e){}
   default void handleKeyboard(KeyEvent e){}
}
