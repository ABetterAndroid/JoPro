package com.joe.pro.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.joe.pro.R;
import com.joe.pro.tool.ViewTools;
import com.joe.pro.view.NetEaseMenuView;

/**
 * Created by qiaorongzhu on 2016/7/21.
 */
public class NetEaseMenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_ease_menu);
        NetEaseMenuView menuView = ViewTools.findView(this, R.id.menu_view);
        menuView.setAdapter(new MyMenuAdapter(new String[]{"test1", "test2",}));
    }

    private class MyMenuAdapter extends NetEaseMenuView.MenuAdapter{

        private String[] itemArray;

        public MyMenuAdapter(String[] itemArray) {
            this.itemArray = itemArray;
        }

        @Override
        public int getCount() {
            return itemArray.length;
        }

        @Override
        public View getView(int position) {

            TextView tvItem = (TextView) LayoutInflater.from(NetEaseMenuActivity.this).inflate(android.R.layout.simple_list_item_1, null);

            tvItem.setText(itemArray[position]);

            return tvItem;
        }
    }
}
