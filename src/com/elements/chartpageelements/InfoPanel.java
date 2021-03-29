package com.elements.chartpageelements;

import com.LLayout.Component.LLabel;
import com.LLayout.Layouts.HorizontalLayout;

public class InfoPanel extends HorizontalLayout {
    public InfoPanel(){
        this.init();
    }

    private void init(){
        this.add(new LLabel("Put info here").setPadding(2, 2));
    }
}
