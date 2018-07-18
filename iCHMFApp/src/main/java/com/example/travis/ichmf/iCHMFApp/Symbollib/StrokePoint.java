package com.example.travis.ichmf.iCHMFApp.Symbollib;

import java.io.Serializable;

/**
 * Created by Travis on 18/7/2018.
 */

public class StrokePoint implements Cloneable, Serializable {

    /**
     * Defautl constructor
     * @param x X location.
     * @param y Y location.
     */
    public StrokePoint (double x, double y){
        X = x;
        Y = y;
    }


    /**
     * X Axis position of stroke point.
     */
    public double X;

    /**
     * Y Axis position of stroke Point.
     */
    public double Y;

    public String ToString()
    {
        return "Point [" + this.X + ", " + this.Y + "]";
    }

//    public Point ToPoint()
//    {
//        return new Point(this.X, this.Y);
//    }

    @Override
    public StrokePoint clone() throws CloneNotSupportedException{
        return (StrokePoint)super.clone();
    }

}
