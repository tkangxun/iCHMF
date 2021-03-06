package com.example.travis.ichmf.iCHMFApp.Main;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import android.view.View;
import android.util.AttributeSet;
import android.content.Context;

import com.example.travis.ichmf.iCHMFApp.Symbollib.Stroke;
import com.example.travis.ichmf.iCHMFApp.Symbollib.StrokeList;
import com.example.travis.ichmf.iCHMFApp.Symbollib.StrokePoint;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by Travis on 18/7/2018.
 */

public class DrawingView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private Context context;


    private static final float TOUCH_TOLERANCE = 4;
    private double delay = 0.01;
    private Instant starts = Instant.now();

    public int _strokeSize = 1;

    private StrokePoint _startPoint, _endPoint;
    private StrokeList _strokes;
    private Stroke _currentStroke;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing(){

        // initialise paint display
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(50);


        mPath = new Path();
        mCanvas = new Canvas();
        //DisplayMetrics metrics = this.context.getResources().getDisplayMetrics();

    }

    //update canvas upon invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    private void touchStart(float x, float y) {
        Instant ends = Instant.now();
        Duration duration = Duration.between(starts, ends);
        // new symbol created if more than delay
        if (duration.getSeconds() >= delay) {
            mPath = new Path();
            _strokes = new StrokeList();
            _strokeSize = 1;
        }
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }
    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                _strokeSize++;
                touchStart(x, y);
                _startPoint = new StrokePoint(x,y);
                _currentStroke = new Stroke();
                _currentStroke.addStrokePoint(_startPoint);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                touchMove(x, y);
                _endPoint = new StrokePoint(x,y);
                _currentStroke.addStrokePoint(_endPoint);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                _strokes.add(_currentStroke);
                _currentStroke = null;
                _startPoint = null;
                _endPoint = null;
                invalidate();
                break;
        }
        starts = Instant.now();
        return true;
    }


    //widgets
    public void clear() {
        mPath.reset();
        _strokeSize = 0;
        invalidate();
    }

    public void setStrokeSize(int ss) {
        _strokeSize = ss;
    }

    public int getStrokeSize() {
        return _strokeSize;
    }

    public void setStrokeList(StrokeList sl) {
        _strokes = sl;
    }

    public Stroke getLastStroke() {
        return this._strokes.get(_strokes.size() - 1);
    }

    public StrokeList getStrokes() {
        return _strokes;
    }

    public void undoLastStroke() {
        this._strokes.remove(_strokes.size() - 1);
        invalidate();
    }
}
