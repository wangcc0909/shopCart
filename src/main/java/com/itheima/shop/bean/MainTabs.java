package com.itheima.shop.bean;

import com.itheima.shop.R;
import com.itheima.shop.fragment.CarFragment;
import com.itheima.shop.fragment.HomeFragment;
import com.itheima.shop.fragment.RegistFragment;

public enum MainTabs {
    HOME(R.string.homeTabHomeTxt, R.drawable.home_tabhost_icon_selector, HomeFragment.class),
    HOT(R.string.homeTabHotTxt, R.drawable.home_tabhost_hot_icon_selector, HomeFragment.class),
    TYPE(R.string.homeTabTypeTxt, R.drawable.home_tabhost_type_icon_selector, HomeFragment.class),
    CAR(R.string.homeTabCarTxt, R.drawable.home_tabhost_car_icon_selector, CarFragment.class),
    MY(R.string.homeTabMyTxt, R.drawable.home_tabhost_my_icon_selector, RegistFragment.class);


    public int txtResId;
    public int iconSelectorId;
    public Class<?> fragmentClazz;


    MainTabs(int txtResId, int iconSelectorId, Class<?> fragmentClazz) {
        this.txtResId = txtResId;
        this.iconSelectorId = iconSelectorId;
        this.fragmentClazz = fragmentClazz;
    }

}
