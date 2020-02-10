package com.renyu.administrator.Cache;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.util.LruCache;

import com.renyu.administrator.Bean.ImageBean;
import com.renyu.administrator.Utils.DiskCacheUtils;

/**
 * 作者：任宇
 * 日期：2020/2/10 19:13
 * 注释：从内存中读取
 */
public class MemoryCacheObservableextends extends CacheObservable{
    public static final String Tag = "MemoryCacheObservableextends";
    private int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private int cacheSize = maxMemory / 4;
    private LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(cacheSize){
        @Override
        protected int sizeOf(String key, Bitmap value) {
            //sizeOf方法就是计算每张图片byte大小的方法
            return value.getRowBytes() * value.getHeight() / 1024;
        }
    };
    @Override
    public ImageBean getDataFromCache(String url) {
        Log.d(Tag, Tag + "three L getDataFromCache  url= "+url);
        Bitmap bitmap = mLruCache.get(url);
        if (bitmap != null) {
            Log.d(Tag, Tag + "three L 从memory 获取数据 ");
        }
        return new ImageBean(bitmap, url);
    }

    @Override
    public void putDataToCache(ImageBean imageBean) {
        Log.d(Tag, Tag + "three L putDataToCache url   "+imageBean.getUrl());
        mLruCache.put(imageBean.getUrl(), imageBean.getBitmap());
    }
}
