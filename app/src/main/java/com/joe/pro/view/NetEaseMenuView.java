package com.joe.pro.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by qiaorongzhu on 2016/7/21.
 */
public class NetEaseMenuView extends LinearLayout {

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
    }


    public abstract class MenuAdapter {

        public abstract int getCount();

        public abstract View getView(int position);

    }
}
