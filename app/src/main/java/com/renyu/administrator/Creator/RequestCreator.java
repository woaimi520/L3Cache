package com.renyu.administrator.Creator;

import android.content.Context;
import android.graphics.Bitmap;

import com.renyu.administrator.Bean.ImageBean;
import com.renyu.administrator.Cache.DiskCacheObservable;
import com.renyu.administrator.Cache.MemoryCacheObservableextends;
import com.renyu.administrator.Cache.NetworkCacheObservable;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;


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
        mDiskCacheObservable = new DiskCacheObservable(context);
        mNetworkCacheObservable = new NetworkCacheObservable();
    }

    public Observable<ImageBean> getImageFromMemory(String url){
        return mMemoryCacheObservableextends.getImage(url).filter(new Predicate<ImageBean>() {
            @Override
            public boolean test(ImageBean imageBean) throws Exception {
                Bitmap bitmap = imageBean.getBitmap();
                return bitmap != null;//表示不为空的时候才发射
            }
        });
    }

    public Observable<ImageBean> getImageFromDisk(String url){
        return mDiskCacheObservable.getImage(url).filter(new Predicate<ImageBean>() {
            @Override
            public boolean test(ImageBean imageBean) throws Exception {
                Bitmap bitmap = imageBean.getBitmap();
                return bitmap != null;//表示不为空的时候才发射
            }
        }).doOnNext(new Consumer<ImageBean>() {
            @Override
            public void accept(ImageBean imageBean) throws Exception {
                //有数据发送出来的时候截断
                mMemoryCacheObservableextends.putDataToCache(imageBean);//因为线读取的是缓存的 能到这里说明缓存里没得 这里寸一份到缓存
            }
        });
    }

    public Observable<ImageBean> getImageFromNetwork(String url){
        return mNetworkCacheObservable.getImage(url).filter(new Predicate<ImageBean>() {
            @Override
            public boolean test(ImageBean imageBean) throws Exception {
                Bitmap bitmap = imageBean.getBitmap();
                return bitmap != null;//表示不为空的时候才发射
            }
        }).doOnNext(new Consumer<ImageBean>() {
            @Override
            public void accept(ImageBean imageBean) throws Exception {
                //有数据发送出来的时候截断
                mMemoryCacheObservableextends.putDataToCache(imageBean);//因为线读取的是缓存的 能到这里说明缓存里没得 这里寸一份到缓存
                mDiskCacheObservable.putDataToCache(imageBean);//因为线读取的是disk的 能到这里说明缓存里没得 这里寸一份到disk

            }
        });
    }

}
