package com.cesu.itcc05.consumeportal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by ITCC05 on 30-11-2019.
 */
public class CustomLineDrawableView extends View {

    Paint fgPaintSel;

    public CustomLineDrawableView(Context context) {
        super(context);
        fgPaintSel= new Paint();
        fgPaintSel.setAlpha(255);
        fgPaintSel.setStrokeWidth(2);
        fgPaintSel.setColor(Color.CYAN);
        fgPaintSel.setStyle(Paint.Style.FILL_AND_STROKE);
        fgPaintSel.setPathEffect(new DashPathEffect(new float[]{2,4},50));
    }

    protected void onDraw(Canvas canvas) {


        canvas.drawLine(100,500,100,300,fgPaintSel);

    }

}
