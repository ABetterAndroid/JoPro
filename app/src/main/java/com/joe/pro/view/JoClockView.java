package com.joe.pro.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by qiaorongzhu on 2016/3/5.
 */
public class JoClockView extends View {

    private Paint mPaint;

    private int[] colors = {Color.RED, Color.BLACK, Color.CYAN, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.GRAY};

    float endAngle;
    private SimpleDateFormat millisecondFormat;
    private SimpleDateFormat secondFormat;
    private SimpleDateFormat minuteFormat;
    private SimpleDateFormat hourFormat;
    private Timer timer;
    private RefreshTimeTask refreshTimeTask;

    public JoClockView(Context context) {
        this(context, null);
    }

    public JoClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JoClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        millisecondFormat = new SimpleDateFormat("SSS");
        secondFormat = new SimpleDateFormat("ss");
        minuteFormat = new SimpleDateFormat("mm");
        hourFormat = new SimpleDateFormat("HH");

        timer = new Timer();
        refreshTimeTask = new RefreshTimeTask();
        timer.scheduleAtFixedRate(refreshTimeTask, 0, 100);

        /*ValueAnimator animator = ValueAnimator.ofFloat(0f, 360f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(2000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                endAngle = (float) animation.getAnimatedValue();
                Log.i("endAngel", endAngle+"");
                postInvalidate();
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mPaint.setColor(colors[new Random().nextInt(colors.length)]);
            }
        });
        animator.start();*/
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        long currentTime = System.currentTimeMillis();
        int millisecond = Integer.valueOf(millisecondFormat.format(currentTime));
        int second = Integer.valueOf(secondFormat.format(currentTime));
        int minute = Integer.valueOf(minuteFormat.format(currentTime));
        int hour = Integer.valueOf(hourFormat.format(currentTime));

//        Log.i("second ", second + "");
        canvas.save();
        canvas.translate(150, 150);  // 移动坐标系到屏幕中心
        canvas.scale(1, -1);
        canvas.drawCircle(0, 0, 100, mPaint);

        drawGraduation(canvas);

        canvas.drawLine(0, 0, getXCoordinate(second, millisecond / 100, 90), getYCoordinate(second, millisecond / 100, 90), mPaint);
        canvas.drawLine(0, 0, getXCoordinate(minute, 0, 80), getYCoordinate(minute, 0, 80), mPaint);
        canvas.drawLine(0, 0, getXCoordinate(hour % 12 * 5, 0, 60), getYCoordinate(hour % 12 * 5, 0, 60), mPaint);

        canvas.restore();
    }

    private void drawGraduation(Canvas canvas) {

        for (int i = 0; i < 60; i++) {

            if (i % 5 == 0) {
                canvas.drawLine(getXCoordinate(i, 0, 91), getYCoordinate(i, 0, 91), getXCoordinate(i, 0, 100), getYCoordinate(i, 0, 100), mPaint);
            } else {
                canvas.drawLine(getXCoordinate(i, 0, 95), getYCoordinate(i, 0, 95), getXCoordinate(i, 0, 100), getYCoordinate(i, 0, 100), mPaint);
            }
        }
    }

    /**
     * 计算指针末端x坐标
     *
     * @param secondClockNumber
     * @param millisecondClockNumber
     * @param clockHandsLength
     * @return
     */
    private float getXCoordinate(int secondClockNumber, int millisecondClockNumber, int clockHandsLength) {
        return Float.parseFloat(String.valueOf(Math.cos(Math.PI / 180 * (90 - (secondClockNumber * 6 + millisecondClockNumber * 0.6))) * clockHandsLength));
    }

    /**
     * 计算指针末端y坐标
     *
     * @param clockNumber
     * @param millisecondClockNumber
     * @param clockHandsLength
     * @return
     */
    private float getYCoordinate(int clockNumber, int millisecondClockNumber, int clockHandsLength) {
        return Float.parseFloat(String.valueOf(Math.sin(Math.PI / 180 * (90 - (clockNumber * 6 + millisecondClockNumber * 0.6))) * clockHandsLength));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timer.cancel();
        timer = null;
        refreshTimeTask = null;
        Log.i("onDetachedFromWindow", "onDetachedFromWindow");
    }

    private class RefreshTimeTask extends TimerTask {

        @Override
        public void run() {
            postInvalidate();
        }
    }
}
