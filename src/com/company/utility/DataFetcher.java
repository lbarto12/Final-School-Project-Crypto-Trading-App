package com.company.utility;

import com.elements.chartpageelements.Chart;
import org.jsoup.Jsoup;


public class DataFetcher extends Thread {

    /**
     * Internal {@code String} holding to fetch data from
     */
    private final String link;

    /**
     * External {@code Chart} to add data points to
     */
    private final Chart chart;

    /**
     * Default Constructor
     *
     * @param chart {@code Chart} to add data to
     * @param link {@code String} link to fetch data from
     */
    public DataFetcher(Chart chart, String link){
        this.chart = chart;
        this.link = link;
    }

    /**
     * Overrides {@code Thread}'s Internal run() method to fetch data
     */
    @Override
    public void run() {
        super.run();
        while(true) {
            try {
                //updateCurrency();
                String str = Jsoup.connect(link).get().toString();
                int ind = str.indexOf("<span class=\"udYkAW2UrhZln2Iv62EYb\">");
                String searchStr = str.substring(ind, ind + 100);
                int dollarIndex = searchStr.indexOf("$");
                String parsed = searchStr.substring(
                        dollarIndex+1,dollarIndex+10
                ).replace(
                        "<", ""
                ).replace(
                        "/", ""
                ).replace(
                        ",", ""
                ).replace(
                        "s", ""
                ).replace(
                        "p", ""
                ).replace(
                        "a", ""
                );
                double newDataPoint = Double.parseDouble(parsed);
                if (chart.getDataSize() == 0 || newDataPoint != chart.lastDataPoint()) {
                    this.chart.addDataPoint(newDataPoint);
                }
                Thread.sleep(100);
            } catch(Exception ignored) {}
        }
    }
}
