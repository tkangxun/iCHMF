package com.example.travis.ichmf.iCHMFApp.Symbollib;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Travis on 18/7/2018.
 */

public class StrokePointList
        extends ArrayList<StrokePoint>
        implements Cloneable, Serializable{

    /** Creates a new instance of StrokePointList */
    public StrokePointList() {
    }

    @Override
    public StrokePointList clone(){
        return (StrokePointList)super.clone();
    }
}
