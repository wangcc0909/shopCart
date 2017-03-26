package com.itheima.shop;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.itheima.shop.bean.MainTabs;
import com.itheima.shop.utils.SizeUtils;
import com.itheima.shop.view.MyFragmentTabHost;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = (MyFragmentTabHost) findViewById(R.id.tab_host);
        initTab();
    }

    MyFragmentTabHost mTabHost;

    private void initTab() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (MainTabs tab : MainTabs.values()) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getResources().getString(tab.txtResId));
            TextView indicatorTextView = (TextView) View.inflate(getBaseContext(), R.layout.home_tabhost_indicator, null);
            Drawable topDrawable = getResources().getDrawable(tab.iconSelectorId);
            int width = SizeUtils.dp2px(getApplicationContext(), 23);
            topDrawable.setBounds(0, 0, width, width);
            indicatorTextView.setCompoundDrawables(null, topDrawable, null, null);
            indicatorTextView.setText(tab.txtResId);
            tabSpec.setIndicator(indicatorTextView);
            mTabHost.addTab(tabSpec, tab.fragmentClazz, null);
        }

    }
}
