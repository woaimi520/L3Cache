package com.renyu.administrator.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 作者：任宇
 * 日期：2020/2/10 21:58
 * 注释：
 */
public class DiskCacheUtils {
    /**
     * 返回disk 缓存的地址
     * @param context
     * @param uniqueName 文件夹名
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (isHasExtra()) {
            cachePath = context.getExternalCacheDir().getPath();//外部私
        } else {
            cachePath = context.getCacheDir().getPath();// 内部缓存地址
        }
        return new File(cachePath + File.separator + uniqueName);

    }

    /**
     * 判断是否有外部sd
     * @return
     */
    public static boolean isHasExtra() {
        return (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable());
    }

    /**
     * @param context
     * @return 获取当前app版本号
     */
    public static Long getApplVersion(Context context){
        long appVersionCode = 0;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                appVersionCode = info.getLongVersionCode();
            } else {
                appVersionCode =info.versionCode;
            }

        } catch (PackageManager.NameNotFoundException e) {
            Log.e("renyu", "e = " + e);
        }
        return appVersionCode;

    }

    public static String getMD5String(String key){
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());//放进去
            cacheKey = bytesToHexString(mDigest.digest());//取出来

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;

    }

    /**
     * byte 8位  FF为只要低八位
     * 把 byte 数组转化为 16进制字符串
     * @param bytes
     * @return
     */
    public static  String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length()==1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
