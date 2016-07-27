package com.joe.pro.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by qiaorongzhu on 2016/7/21.
 */
public class NetEaseMenuView extends FrameLayout implements View.OnClickListener {

    private int width;
    private int left;
    private int height;

    public NetEaseMenuView(Context context) {
        super(context);
    }

    public NetEaseMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetEaseMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(MenuAdapter mAdapter) {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            addView(mAdapter.getView(i));
        }
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(android.R.drawable.ic_menu_more);
        imageView.setOnClickListener(this);
        addView(imageView);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = width == 0 ? w : width;
        left = left == 0 ? w : left;
        height = height == 0 ? h : height;
    }

    @Override
    public void onClick(View v) {
        removeViews(0, 3);
        ValueAnimator moreAnimator = ValueAnimator.ofFloat(0, 1);
        moreAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                Log.i("jo", width + " " + (50 * (Float) animation.getAnimatedValue()));
                float deltaValue = 300 * (Float) animation.getAnimatedValue();
                getLayoutParams().width = (int) (width - deltaValue);
                getLayoutParams().height = (int) (height + deltaValue);
                requestLayout();
            }
        });
        moreAnimator.setDuration(300);
        moreAnimator.start();

//        setLeft(getLeft() + 500);
//        setTop(getTop() - 200);
    }


    public static abstract class MenuAdapter {

        public MenuAdapter(){}

        public abstract int getCount();

        public abstract View getView(int position);

    }
}
