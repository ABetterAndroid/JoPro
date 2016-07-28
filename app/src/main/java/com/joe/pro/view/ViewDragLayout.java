package com.joe.pro.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by qiaorongzhu on 2016/7/26.
 */
public class ViewDragLayout extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View dragView;
    private VelocityTracker mVelocityTracker;
    private int pointerId;
    private int leftBound;
    private int rightBound;
    private int topBound;
    private int bottomBound;
    private float mMaxVelocity;
    private final float FLING_TIME = 0.15f;

    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewDragLayout(Context context) {
        super(context);
    }

    public ViewDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mVelocityTracker = VelocityTracker.obtain();

        mMaxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return dragView == child;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.i("dragView H", left + " " + dx);
                int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.i("dragView V", top + " " + dy);
                int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                mViewDragHelper.settleCapturedViewAt((int) Math.min(Math.max(dragView.getLeft() + xvel * FLING_TIME, leftBound), rightBound),
                        (int) Math.min(Math.max(dragView.getTop() + yvel * FLING_TIME, topBound), bottomBound));
                invalidate();

            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        dragView = getChildAt(0);
        leftBound = getPaddingLeft();
        rightBound = getWidth() - dragView.getWidth();
        topBound = getPaddingTop();
        bottomBound = getHeight() - dragView.getHeight();
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        mViewDragHelper.processTouchEvent(ev);
        mVelocityTracker.addMovement(ev);
        pointerId = MotionEventCompat.getPointerId(ev, 0);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            if (dragView.getLeft() >= rightBound || dragView.getLeft() <= 0) {
                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                float xvel = -VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId) * 0.5f;
                float yvel = VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId) * 0.5f;
                mViewDragHelper.abort();
                mViewDragHelper.smoothSlideViewTo(dragView, (int) (Math.min(Math.max(dragView.getLeft() + xvel * FLING_TIME * 0.1, leftBound), rightBound)),
                        (int) Math.min(Math.max(dragView.getTop() + yvel * FLING_TIME * 0.1, topBound), bottomBound));
                invalidate();
            } else if (dragView.getTop() >= bottomBound || dragView.getTop() <= 0) {
                mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                float xvel = VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId) * 0.5f;
                float yvel = -VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId) * 0.5f;
                mViewDragHelper.abort();
                mViewDragHelper.smoothSlideViewTo(dragView, (int) (Math.min(Math.max(dragView.getLeft() + xvel * FLING_TIME * 0.1, leftBound), rightBound)),
                        (int) Math.min(Math.max(dragView.getTop() + yvel * FLING_TIME * 0.1, topBound), bottomBound));
                invalidate();
            } else {
                invalidate();
            }
        }
    }
}
