package com.renyu.administrator.Creator;

import android.content.Context;

import com.renyu.administrator.Bean.ImageBean;
import com.renyu.administrator.Cache.DiskCacheObservable;
import com.renyu.administrator.Cache.MemoryCacheObservableextends;
import com.renyu.administrator.Cache.NetworkCacheObservable;

import io.reactivex.Observable;


/**
 * 作者：任宇
 * 日期：2020/2/10 19:19
 * 注释：管理缓存类
 */
public class RequestCreator {
    public MemoryCacheObservableextends mMemoryCacheObservableextends;
    public DiskCacheObservable mDiskCacheObservable;
    public NetworkCacheObservable mNetworkCacheObservable;

    public RequestCreator(Context context){
        mMemoryCacheObservableextends = new MemoryCacheObservableextends();
        mDiskCacheObservable = new DiskCacheObservable();
        mNetworkCacheObservable = new NetworkCacheObservable();
    }

    public Observable<ImageBean> getImageFromMemory(String url){
        return mMemoryCacheObservableextends.getImage(url);
    }

    public Observable<ImageBean> getImageFromDisk(String url){
        return mDiskCacheObservable.getImage(url);
    }

    public Observable<ImageBean> getImageFromNetwork(String url){
        return mNetworkCacheObservable.getImage(url);
    }

}
