package com.renyu.administrator.Cache;

import android.nfc.Tag;

import com.renyu.administrator.Bean.ImageBean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 作者：任宇
 * 日期：2020/2/10 18:43
 * 注释：
 */
public abstract class CacheObservable {
    public static final String Tag = "CacheObservable";
    public Observable<ImageBean> getImage(final String url){
        return Observable.create(new ObservableOnSubscribe<ImageBean>() {
            @Override
            public void subscribe(ObservableEmitter<ImageBean> emitter) throws Exception {

                if (!emitter.isDisposed()) {
                    ImageBean imageBean = getDataFromCache(url);
                    emitter.onNext(imageBean);
                    emitter.onComplete();
                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 取出数据
     * @param url
     * @return
     */
    public abstract ImageBean getDataFromCache(String url);

    /**
     * 缓存数据
     * @param imageBean
     */
    public abstract void putDataToCache(ImageBean imageBean);
}
