package com.gurutre.twister;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by gurutre on 8/30/14.
 */
public class CircleView extends View {

    //Thanks to Adanware a lot of this is based on his
    //exmple at: http://adanware.blogspot.com/2012/11/android-creating-custom-view-circle.html
    private Paint circlePaint;
    private Paint circleStrokePaint;

    private int circleRadius;
    private int circleFillColor;
    private int circleStrokeColor;
    private boolean startAtScreenOrigin;

    public CircleView(Context c){
        super(c);
        new CircleView(c,null);
    }

    public CircleView(Context c, AttributeSet a){
        super(c,a);
        if(a != null){
            init(a); // Read all attributes

            circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            circlePaint.setStyle(Paint.Style.FILL);
            circleStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            circleStrokePaint.setStyle(Paint.Style.STROKE);
            circleStrokePaint.setStrokeWidth(2);
            circleStrokePaint.setColor(circleStrokeColor);
        }
    }
    public void setStrokeColor(int color){
        circleStrokeColor = color;
        circleStrokePaint.setColor(circleStrokeColor);
    }
    public void setFillColor(int color){
        circleFillColor = color;
        circleStrokePaint.setColor(circleFillColor);
    }
    public int getStrokeColor(){ return circleStrokeColor; }
    public int getFillColor(){ return circleFillColor; }
    public void setCircleRadius(int radius){ circleRadius = radius; }
    public int getCircleRadius(){ return circleRadius;}
    public void setStartAtScreenOrigin(boolean s){ this.startAtScreenOrigin = s; }
    public boolean getStarAtScreenOrigin(){ return startAtScreenOrigin; }

    private void init(AttributeSet attrs)
    {
        // Go through all custom attrs.
        TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.circleview);
        circleRadius = attrsArray.getInteger(R.styleable.circleview_radius, 0);
        circleFillColor = attrsArray.getColor(R.styleable.circleview_fillColor, 16777215);
        circleStrokeColor = attrsArray.getColor(R.styleable.circleview_strokeColor, -1);
        startAtScreenOrigin =true;

        // Google tells us to call recycle.
        attrsArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Move canvas down and right 1 pixel.
        // Otherwise the stroke gets cut off.
        canvas.translate(1,1);
        super.onDraw(canvas);
        circlePaint.setColor(circleFillColor);
        int x,y;
        if(startAtScreenOrigin){
            x = y = 0;
        }else{
            x=0;
            y=0;
        }
        canvas.drawCircle(x,y,(float)circleRadius,circlePaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int measuredWidth = measureWidth(widthMeasureSpec);
        if(circleRadius == 0) {
            circleRadius = measuredWidth / 2;
            int tempRadiusHeight = measureHeight(heightMeasureSpec) / 2;
            if(tempRadiusHeight < circleRadius)
                // Check height, if height is smaller than
                // width, then go half height as radius.
                circleRadius = tempRadiusHeight;
        }
        // Remove 2 pixels for the stroke.
        int measuredHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
        Log.d("onMeasure() ::", "measuredHeight =>" + String.valueOf(measuredHeight) + "px measuredWidth => " + String.valueOf(measuredWidth) + "px");
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST)
            result = circleRadius * 2;
        else if (specMode == MeasureSpec.EXACTLY)
            result = specSize;
        return result;
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST)
            result = specSize;
        else if (specMode == MeasureSpec.EXACTLY)
            result = specSize;
        return result;
    }
}
