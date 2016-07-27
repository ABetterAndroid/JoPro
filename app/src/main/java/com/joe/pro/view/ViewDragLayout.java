package com.joe.pro.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by qiaorongzhu on 2016/7/26.
 */
public class ViewDragLayout extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View dragView;

    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewDragLayout(Context context) {
        super(context);
    }

    public ViewDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return dragView == child;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.i("dragView H", left + " " + dx);
                int leftBound = getPaddingLeft();
                int rightBound = getWidth() - dragView.getWidth();
                int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.i("dragView V", top + " " + dy);
                int topBound = getPaddingTop();
                int bottomBound = getHeight() - dragView.getHeight();
                int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                int leftBound = getPaddingLeft();
                int rightBound = getWidth() - dragView.getWidth();
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - dragView.getHeight();
                mViewDragHelper.settleCapturedViewAt((int) (Math.min(Math.max(dragView.getLeft() + xvel * 0.2, leftBound), rightBound)),
                        (int) Math.min(Math.max(dragView.getTop() + yvel * 0.2, topBound), bottomBound));
                invalidate();

            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        dragView = getChildAt(0);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mViewDragHelper.processTouchEvent(ev);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
