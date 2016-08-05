package com.joe.pro.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.joe.pro.R;

/**
 * Created by qiaorongzhu on 2016/8/5.
 */
public class WaveView extends View {

    private static final int HORIZONTAL_PART_COUNT = 200;
    private static final int VERTICAL_PART_COUNT = 200;
    private float[] verts = new float[(HORIZONTAL_PART_COUNT + 1) * (VERTICAL_PART_COUNT + 1) * 2];
    private Bitmap waterBitmap;
    private float animatedValue;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        prepareVerts();
        startAnimation();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int index = 0;
        for (int i = 0; i <= VERTICAL_PART_COUNT; i++) {
            for (int j = 0; j <= HORIZONTAL_PART_COUNT; j++) {
                float delt = (float) (50 * Math.sin((float) j / HORIZONTAL_PART_COUNT * 4 * Math.PI + animatedValue));
                verts[index * 2 + 1] += delt;
                verts[index * 2] *= 6;
                index++;
            }
        }
        canvas.drawBitmapMesh(waterBitmap, HORIZONTAL_PART_COUNT, VERTICAL_PART_COUNT, verts, 0, null, 0, null);

    }

    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, Float.parseFloat(2 * Math.PI + ""));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (float) animation.getAnimatedValue();
                prepareVerts();
                postInvalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.start();
    }

    private void prepareVerts() {
        waterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water);
        int waterWidth = waterBitmap.getWidth();
        int waterHeight = waterBitmap.getHeight();
        int index = 0;
        for (int i = 0; i <= VERTICAL_PART_COUNT; i++) {
            float y = waterHeight * i / VERTICAL_PART_COUNT;
            for (int j = 0; j <= HORIZONTAL_PART_COUNT; j++) {
                float x = waterWidth * j / HORIZONTAL_PART_COUNT;
                verts[index * 2] = x;
                verts[index * 2 + 1] = y + 100;
                index++;
            }
        }
    }
}
