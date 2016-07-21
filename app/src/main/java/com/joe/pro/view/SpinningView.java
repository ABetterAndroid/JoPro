package com.joe.pro.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by qiaorongzhu on 2016/4/6.
 */
public class SpinningView extends View {

    private Paint mPaint;
    private float currentTranslateValue;
    private int horizonCircleRadius;

    public SpinningView(Context context) {
        super(context);
    }

    public SpinningView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SpinningView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        horizonCircleRadius = getMeasuredWidth() / 8;
        Log.i("SpinningView Radius", horizonCircleRadius + "");
        ValueAnimator translateAnimator = ValueAnimator.ofFloat(0f, (float) (2*Math.PI));
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatatedValue = (float) animation.getAnimatedValue();

                /*if (animatatedValue >= 0 && animatatedValue < 2) {
                    currentTranslateValue = (float) -Math.sqrt(Math.abs(Math.pow(horizonCircleRadius, 2) - Math.pow(animatatedValue * horizonCircleRadius - horizonCircleRadius, 2)));

                } else if (animatatedValue >= 2 && animatatedValue <= 4) {
                    currentTranslateValue = (float) Math.sqrt(Math.abs(Math.pow(horizonCircleRadius, 2) - Math.pow(animatatedValue * horizonCircleRadius - 3 * horizonCircleRadius, 2)));
                }*/

                currentTranslateValue = (float) (40 * Math.sin(animatatedValue));

                Log.i("currentValue ", currentTranslateValue + "");


                postInvalidate();
            }
        });

        translateAnimator.setDuration(4000);
        translateAnimator.setRepeatMode(ValueAnimator.RESTART);
//        translateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        translateAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        canvas.scale(1, -1);

        canvas.drawCircle(currentTranslateValue, 0, 10, mPaint);

        canvas.restore();

    }
}
