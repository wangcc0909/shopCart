package com.itheima.shop.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.AttributeSet;


public class MyFragmentTabHost extends FragmentTabHost {
    public MyFragmentTabHost(Context context) {
        this(context, null);
    }

    public MyFragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setup(Context context, FragmentManager manager, int containerId) {
        super.setup(context, manager, containerId);
        getTabWidget().setDividerDrawable(null);
    }

    @Override
    public void onTabChanged(String tabId) {
        if (!TextUtils.isEmpty(tabId)) {
            super.onTabChanged(tabId);
        }
    }
}
