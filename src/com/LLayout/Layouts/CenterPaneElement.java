package com.LLayout.Layouts;

import com.LLayout.Utility.LObject;

public class CenterPaneElement{
    public CenterPaneElement(LObject element, CPConstraints constraints){
        this.self = element;
        this.con = constraints;
    }
    public LObject self;
    public CPConstraints con;
}