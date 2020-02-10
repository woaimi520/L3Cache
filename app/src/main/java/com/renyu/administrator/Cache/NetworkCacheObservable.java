package com.renyu.administrator.Cache;

import com.renyu.administrator.Bean.ImageBean;

/**
 * 作者：任宇
 * 日期：2020/2/10 19:14
 * 注释：从网络读取
 */
public class NetworkCacheObservable extends CacheObservable{
    @Override
    public ImageBean getDataFromCache(String url) {
        return null;
    }

    @Override
    public void putDataToCache(ImageBean imageBean) {

    }
}
