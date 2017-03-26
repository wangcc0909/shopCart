package com.itheima.shop.application;

import android.app.Application;
import android.content.Context;

import com.itheima.retrofitutils.ItheimaHttp;

/**
 * Created by mwqi on 2017/3/15.
 */

public class JDApp extends Application {


    public static Context mContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //json数据在项目readme里面请放置到toamcat
        ItheimaHttp.init(mContext, "http://127.0.0.1:8080/api/");
    }
}
