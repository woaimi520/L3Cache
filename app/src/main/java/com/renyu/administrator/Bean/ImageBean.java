package com.renyu.administrator.Bean;

import android.graphics.Bitmap;

/**
 * 作者：任宇
 * 日期：2020/2/10 18:17
 * 注释：
 */
public class ImageBean {
    private String url;
    private Bitmap mBitmap;

    public ImageBean(Bitmap bitmap, String url) {
        this.mBitmap = bitmap;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}
