package com.company.utility;

import com.company.Main;
import com.elements.chartpageelements.Chart;
import org.jsoup.Jsoup;

import java.util.ArrayList;

public class DataFetcher extends Thread {
    String link;
    Chart chart;

    public DataFetcher(Chart chart, String link){
        this.chart = chart;
        this.link = link;
    }

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
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
