package com.joe.pro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.joe.pro.R;
import com.joe.pro.Tool.ViewTools;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lvDemos = ViewTools.findView(this, R.id.demo_list);
        lvDemos.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"ClockView", "AnimationTest", "NetEase-Style menu"}));
        lvDemos.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(this, ClockActivity.class);
                break;
            case 1:
                intent.setClass(this, AnimationActivity.class);
                break;
            case 2:
                intent.setClass(this, NetEaseMenuActivity.class);
                break;
        }
        startActivity(intent);
    }
}
