package com.renyu.administrator.Loader;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.renyu.administrator.Bean.ImageBean;
import com.renyu.administrator.Creator.RequestCreator;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：任宇
 * 日期：2020/2/10 19:40
 * 注释：上层直接调用的接口
 */
public class RxImageLoader {
    static RxImageLoader singleton;
    private String mUrl;
    private RequestCreator requestCreator;
    private  WeakReference<Context> mWeakContext;

    private RxImageLoader() {

    }

    public static RxImageLoader with(Context context) {
        if (singleton == null) {
            synchronized (RxImageLoader.class) {
                if (singleton == null) {
                    singleton = new RxImageLoader();
                }
            }
        }
        singleton.mWeakContext = new WeakReference<>(context);
        return singleton;
    }

    public RxImageLoader load(String url) {
        this.mUrl = url;
        return singleton;
    }

    public void into(final ImageView imageView) {
        requestCreator = new RequestCreator(mWeakContext.get());
        Observable.concat(
                requestCreator.getImageFromMemory(mUrl),
                requestCreator.getImageFromDisk(mUrl),
                requestCreator.getImageFromNetwork(mUrl)
        ).first(new ImageBean(null, mUrl)).toObservable().subscribe(new Observer<ImageBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ImageBean imageBean) {
                if (imageBean.getBitmap() != null) {
                    imageView.setImageBitmap(imageBean.getBitmap());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("renyu", "e =" + e);
            }

            @Override
            public void onComplete() {
                Log.i("renyu", "onComplete");
            }
        });


    }


}
