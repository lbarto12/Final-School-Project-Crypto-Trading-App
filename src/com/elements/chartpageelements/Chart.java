package com.elements.chartpageelements;

import com.LLayout.Component.LCanvas;
import com.LLayout.Utility.Vector2L;
import com.company.Main;
import com.company.utility.DataFetcher;
import com.company.utility.DataPoint;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Chart extends LCanvas implements MouseWheelListener, MouseMotionListener, MouseListener {

    /**
     * Target window
     */
    private final Main window;

    /**
     * Internal {@code XAxis} to display times
     */
    private final XAxis xAxis;

    /**
     * Internal {@code YAxis} to display price points
     */
    private final YAxis yAxis;

    /**
     * Default Constructor
     *
     * @param window Target window
     * @param xAxis {@code XAxis} to display times on
     * @param yAxis {@code YAxis} to display prices on
     * @param simulated to denote whether this object is simulated
     */
    public Chart(Main window, XAxis xAxis, YAxis yAxis, Boolean simulated){
        this.window = window;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.init(simulated);
    }

    /**
     * What percentage of the chart is being shown
     */
    private int rangeShowing = 100;

    /**
     * Where to begin showing data from
     */
    private float minPoint = 1;

    /**
     * Specific element range to show
     */
    private int limit;

    /**
     * Internal {@code ArrayList<Double>} to store data point values
     */
    public ArrayList<Double> currentData = new ArrayList<>();

    /**
     * Internal {@code ArrayList<Long>} to store time point values
     */
    public ArrayList<Long> currentTimes = new ArrayList<>();

    /**
     * Initialize this. Adds component listeners, creates {@code DataFetcher} & loads
     * pre-existing data from data.csv file
     *
     * @param simulated Denotes if this should be a simulated object
     */
    private void init(Boolean simulated){
        this.window.addMouseWheelListener(this);
        this.window.addMouseMotionListener(this);
        this.window.addMouseListener(this);

        this.setFillColor(Color.BLACK);


        if (!simulated){
            new DataFetcher(this,"https://robinhood.com/crypto/BTC").start();

            for (var i : Chart.loadDataPoints()){
                this.currentData.add(i.price);
                this.currentTimes.add(i.time);
            }
        }
    }


    private ArrayList<Double> tempData;
    private ArrayList<Long> tempTimes;

    /**
     * paintComponent Override
     *
     * Draws chart
     *
     * @param g {@code Graphics2D} object
     */
    @Override
    public void paintComponent(Graphics2D g) {
        super.paintComponent(g);

        g.setColor(Color.darkGray);

        for (int i = 0; i < 10; ++i){
            // Vertical
            g.drawLine(
                    i * (this.visibleBounds.width / 10) + this.visibleBounds.x, this.visibleBounds.y,
                    i * (this.visibleBounds.width / 10) + this.visibleBounds.x, this.visibleBounds.y + this.visibleBounds.height
            );

            // Horizontal
            g.drawLine(
                    this.visibleBounds.x, i * (this.visibleBounds.height / 10)  + this.visibleBounds.y,
                    this.visibleBounds.x + this.visibleBounds.width, i * (this.visibleBounds.height / 10) + this.visibleBounds.y
            );
        }


        g.setColor(Color.ORANGE);
        if(currentData.size() > 1) {

            this.limit = (int)(this.currentData.size() * (this.rangeShowing / 100.f));

            if (this.currentData.size() - minPoint < limit){
                this.limit = this.currentData.size() - (int)minPoint;
            }


            if (minPoint < 1) minPoint = 1;




            try {
                tempData = new ArrayList<>(this.currentData.subList((int)minPoint, (int)(minPoint + limit)));
                tempTimes = new ArrayList<>(this.currentTimes.subList((int)minPoint, (int)(minPoint + limit)));
            } catch (Exception e){
                tempData = new ArrayList<>();
                tempTimes = new ArrayList<>();
            }

            this.xAxis.updateLabels(tempTimes);
            this.yAxis.updateLabels(tempData);


            double _max = Collections.max(tempData), _min = Collections.min(tempData);
            double max = _max + ((_max - _min) * .1f);
            double min = _min - ((_max - _min) * .1f);
            double xStep = (double) this.visibleBounds.width / ((double) limit);
            double yStep = (double) this.visibleBounds.height / (max - min);

            for (int i = (int)minPoint; i <= minPoint + limit && i < currentData.size(); i++) {
                if (i == minPoint + limit || i == currentData.size() - 1) g.setColor(Color.GREEN);
                g.drawLine(
                        this.visibleBounds.x + (int)xStep + (int) (xStep * (i - 1 - minPoint)),
                        this.visibleBounds.y + (int) (yStep * (max - currentData.get(i - 1))),
                        this.visibleBounds.x + (int)xStep + (int) (xStep * (i - minPoint)),
                        this.visibleBounds.y + (int) (yStep * (max - currentData.get(i)))
                );
            }
        }

    }


    /**
     * Add new data point to {@code this.currentData} & {@code this.currentTimes}
     *
     * @param point Price to add
     */
    public void addDataPoint(Double point){
        this.currentData.add(point);
        this.currentTimes.add(System.currentTimeMillis());
        if (rangeShowing != 100 && (int)(this.minPoint + this.limit + 1) == this.currentData.size()){
            minPoint++;
        }

        // Should save the point to the static arraylist, makes it easy to save to file
        new DataPoint(this.currentData.get(currentData.size() - 1), this.currentTimes.get(currentTimes.size() - 1));

        this.window.repaint();
    }

    /**
     *
     * @return data set size
     */
    public int getDataSize(){
        return this.currentData.size();
    }

    /**
     * @return most recent data point added
     */
    public Double lastDataPoint(){
        return this.currentData.get(this.currentData.size() - 1);
    }

    /**
     * Writes {@code DataPoint.all} to csv file
     *
     * @throws IOException
     */
    public static void saveDataPoints() throws IOException {
        try (var out = new BufferedWriter(new FileWriter("data.csv", true))){
            for (var i : DataPoint.all){
                out.write(String.valueOf(i.price));
                out.write(',');
                out.write(String.valueOf(i.time));
                out.write('\n');
            }

        }
    }

    /**
     * @return loaded {@code ArrayList<DataPoint>} from data.csv
     */
    public static ArrayList<DataPoint> loadDataPoints() {
        var temp = new ArrayList<DataPoint>();

        try (var in = new BufferedReader(new FileReader("data.csv"))){

            String line = in.readLine();
            while (!line.equals("")){
                var split = line.split(",");
                temp.add(new DataPoint(
                        Double.valueOf(split[0]),
                        Long.valueOf(split[1]),
                        false
                        )
                );
                line = in.readLine();
            }

        } catch (Exception ignored){ }

        return temp;
    }

    /**
     * Implements {@code Interface MouseWheelListener}
     *
     * Changes amount displayed depending on scroll value
     *
     * @param e scroll event
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0){
            this.rangeShowing++;
            if (minPoint > 1) minPoint--;
        }
        else if (e.getWheelRotation() < 0){
            this.rangeShowing--;
            if (minPoint < this.currentData.size() - 5 && this.rangeShowing > 10) minPoint++;
        }


        if (this.rangeShowing < 10)
            this.rangeShowing = 10;
        else if (this.rangeShowing > 100)
            this.rangeShowing = 100;


        this.window.repaint();
    }


    /**
     * Implements {@code Interface MouseMotionListener}
     *
     * Changes position displaying depending on drag ratio
     *
     * @param e mouse dragged event
     */
    @Override
    public void mouseDragged(MouseEvent e) {


        System.out.println("min = " + minPoint);
        System.out.println("limit = " + limit);
        System.out.println("min + lim = " + (int)(this.minPoint + limit));
        System.out.println("length = " + this.currentData.size());

        if (this.clickPos.x - e.getX() < 0 && this.minPoint > 0 && rangeShowing != 100){
            float factor = (float) (clickPos.x - e.getX()) / (float) this.visibleBounds.width;
            float amount = limit * factor;
            this.minPoint += amount;
        }
        else if (this.clickPos.x - e.getX() > 0 &&  (int)(this.minPoint + limit) != this.currentData.size() && rangeShowing != 100){
            float factor = (float) (clickPos.x - e.getX()) / (float) this.visibleBounds.width;
            float amount = limit * factor;
            this.minPoint += amount;
        }
        if (this.minPoint < 0) this.minPoint = 0;

        this.clickPos.x = e.getX();
        this.clickPos.y = e.getY();

        window.repaint();
    }

    /**
     * Stores position of click
     */
    private final Vector2L<Integer> clickPos = new Vector2L<>(0, 0);

    /**
     * Implements {@code Interface MouseListener}
     *
     * Sets clickPos to mouse position
     *
     * @param e mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.clickPos.x = e.getX();
        this.clickPos.y = e.getY();
    }


    /**
     * Subsequent unused {@code Interface} implementations
     *
     * @param e event
     */
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
