package com.joe.pro.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.widget.ImageView;

import com.joe.pro.R;
import com.joe.pro.Tool.ViewTools;

/**
 * Created by qiaorongzhu on 2016/7/21.
 */
public class AnimationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ImageView ivAnim = ViewTools.findView(this, R.id.path_anim);
        ((Animatable)ivAnim.getDrawable()).start();
    }
}
