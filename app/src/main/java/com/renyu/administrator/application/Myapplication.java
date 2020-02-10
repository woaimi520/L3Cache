package com.renyu.administrator.application;

import android.app.Application;

import com.renyu.administrator.Loader.RxImageLoader;

/**
 * 作者：任宇
 * 日期：2020/2/11 2:08
 * 注释：
 */
public class Myapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxImageLoader.init(this);
    }
}
