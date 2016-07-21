package com.joe.pro.Tool;

import android.app.Activity;
import android.view.View;

/**
 * Created by qiaorongzhu on 2016/7/21.
 */
public class ViewTools {

    public static <T extends View> T findView(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }

}
