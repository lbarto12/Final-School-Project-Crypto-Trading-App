package com.company;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main(){
        this.setTitle("Crypto Trade");
        this.setSize(new Dimension(800, 500));
        this.setMinimumSize(new Dimension(1000, 600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    public static void main(String[] args) {
        new Controller(new Main());
    }
}
