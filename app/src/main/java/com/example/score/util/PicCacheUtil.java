package com.example.score.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.score.cache.LocalCache;
import com.example.score.cache.MemoryCache;
import com.example.score.cache.NetCache;

import java.io.File;
import java.lang.ref.SoftReference;

public class PicCacheUtil {
    private static PicCacheUtil instance;
    public static String CACHE_PATH;
    public static int DEFAULT_PIC;
    private final MemoryCache mMemoryCache;
    private final LocalCache mLocalCache;
    private final NetCache mNetCache;
    private SoftReference<Bitmap> mSoftReference;

    public void init(String CACHE_PATH, int DEFAULT_PIC) {
        this.CACHE_PATH = CACHE_PATH;
        this.DEFAULT_PIC = DEFAULT_PIC;
    }

    private PicCacheUtil() {
        mMemoryCache = new MemoryCache();
        mLocalCache = new LocalCache();
        mNetCache = new NetCache(mLocalCache, mMemoryCache);
    }

    public static PicCacheUtil getInstance() {
        if (instance == null) {
            synchronized (PicCacheUtil.class) {
                if (instance == null) {
                    instance = new PicCacheUtil();
                }
            }
        }
        return instance;
    }


    public void display(ImageView iv, String url) {

        iv.setImageResource(this.DEFAULT_PIC);

        //先从内存中获取
        Bitmap btp = mMemoryCache.getBitmapFromMemory(url);
        if (btp != null) {
            iv.setImageBitmap(btp);
            return;
        }

        //内存中没有，从本地获取图片
        btp = mLocalCache.getBitmapFromLocal(url);
        if (btp != null) {
            iv.setImageBitmap(btp);
            mMemoryCache.put(url, btp);//放入LruCache中
            System.out.println("从本地获取图片.....");
            return;
        }
        //最后才走网络获取图片
        mNetCache.getBitmapFromNet(iv, url);
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        File file = new File(this.CACHE_PATH);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            System.out.println( "缓存文件夹文件数量" + file.listFiles().length);
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                f.delete();
            }
        }
        System.out.println( "缓存文件夹文件数量" + file.listFiles().length);
    }

}

