package com.renyu.administrator.Cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.renyu.administrator.Bean.ImageBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 作者：任宇
 * 日期：2020/2/10 19:14
 * 注释：从网络读取
 */
public class NetworkCacheObservable extends CacheObservable{
    public static final String Tag = "NetworkCacheObservable";
    @Override
    public ImageBean getDataFromCache(String url) {
        Log.d(Tag, Tag + "three L getDataFromCache  ");
        Bitmap bitmap = downloadImage(url);
        return new ImageBean(bitmap, url);
    }

    @Override
    public void putDataToCache(ImageBean imageBean) {
        //什么都不做
    }

    public Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream inputStream = null;

        try {
            URL imageUrl = new URL(url);
            URLConnection urlConnection = (HttpURLConnection) imageUrl.openConnection();
            inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);//这里和disk读取有所区别 disk是直接写入到文件 这里是转为bitmap
        } catch (Exception e) {
            Log.d(Tag, Tag + "three L downloadImage e = " + e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }
}
