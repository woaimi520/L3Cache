package com.renyu.administrator.Cache;

import com.renyu.administrator.Bean.ImageBean;

/**
 * 作者：任宇
 * 日期：2020/2/10 19:13
 * 注释：从内存中读取
 */
public class MemoryCacheObservableextends extends CacheObservable{
    @Override
    public ImageBean getDataFromCache(String url) {
        return null;
    }

    @Override
    public void putDataToCache(ImageBean imageBean) {

    }
}
