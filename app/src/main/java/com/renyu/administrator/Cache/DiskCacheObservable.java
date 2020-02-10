package com.renyu.administrator.Cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.renyu.administrator.Bean.ImageBean;
import com.renyu.administrator.Utils.DiskCacheUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：任宇
 * 日期：2020/2/10 19:13
 * 注释：从硬盘里读取
 */
public class DiskCacheObservable extends CacheObservable{
    private Context mContext;
    private DiskLruCache mDiskLruCache;
    private final int maxSize = 10 * 1024 * 1024;

    public DiskCacheObservable(Context mContext) {
        this.mContext = mContext;
        initDiskLruCache();
    }
    @Override
    public ImageBean getDataFromCache(String url) {
        Log.d("renyu","getDataFromDiskCache");
        Bitmap bitmap = getDataFromDiskLruCache(url);
        return new ImageBean(bitmap, url);
    }

    @Override
    public void putDataToCache(final ImageBean imageBean) {
        Observable.create(new ObservableOnSubscribe<ImageBean>() {

            @Override
            public void subscribe(ObservableEmitter<ImageBean> emitter) throws Exception {
                putDataToDiskL如Cache(imageBean);
            }
        }).subscribeOn(Schedulers.io()).subscribe();

    }

    public void initDiskLruCache(){
        File cacheDir = DiskCacheUtils.getDiskCacheDir(mContext, "my_cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        Long versionCode = DiskCacheUtils.getApplVersion(mContext);
        int versionCodeInt = (Integer) versionCode.intValue();
        try {
            mDiskLruCache = DiskLruCache.open(cacheDir, (int)versionCodeInt, 1, maxSize);
        } catch (IOException e) {
            Log.d("renyu", "e ==" + e);
        }

    }

    /**
     * 用key 来读取 bitmap
     * @param url
     * @return
     */
    private Bitmap getDataFromDiskLruCache(String url){
        Bitmap bitmap = null;
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        final String key = DiskCacheUtils.getMD5String(url);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);//1 通过key 获取 快照
            fileInputStream = (FileInputStream)snapshot.getInputStream(0); //2 通过快照 获取 文件输出来的流
            fileDescriptor = fileInputStream.getFD();
            if (fileDescriptor != null) {
                bitmap = BitmapFactory.decodeStream(fileInputStream);//3 流转为bitmap
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    private void putDataToDiskL如Cache(ImageBean imageBean) {
        String key = DiskCacheUtils.getMD5String(imageBean.getUrl());
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);//1 得到编辑器
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);//2 得到输入文件的流 这个留不是直接输入的
                boolean isSuccess = downloadUrlToStream(imageBean.getUrl(), outputStream);
                if (isSuccess) {
                    editor.commit();//3 写入文件
                } else {
                    editor.abort();//3 放弃写入
                }
                mDiskLruCache.flush();//写记录数据到记录文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     * @param  urlString 图片的url 未转换的
     * @param outputStream 输入到文件的流
     * @return
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
                urlConnection = (HttpURLConnection)url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);//流输出到文件 需要最后  editor.commit();才会写入
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
