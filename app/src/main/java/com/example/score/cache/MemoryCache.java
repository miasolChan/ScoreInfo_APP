package com.example.score.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 内存缓存
 */
public class MemoryCache extends LruCache<String, Bitmap> {

    private LinkedHashMap<String, SoftReference<Bitmap>> mSoftCacheMap;
    private SoftReference<Bitmap> softReference;

    public MemoryCache() {
        //一般设置内存大小占系统内存的1/8
        super((int) (Runtime.getRuntime().maxMemory() / 8));
        this.mSoftCacheMap = new LinkedHashMap<String, SoftReference<Bitmap>>();
    }

    public Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap = get(url);
        if (bitmap != null) {
            System.out.println("从内存强引用中获取图片....");
            return bitmap;
        } else {
            softReference = mSoftCacheMap.get(url);
            if (softReference != null) {
                bitmap = softReference.get();
                if (bitmap != null) {
                    System.out.println("从内存软引用中获取图片.....");
                    this.put(url, bitmap);
                    return bitmap;
                }
            }
        }
        return null;
    }

    @Override // 获取图片大小
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override // 当有图片从强引用中移除时，将其放进软引用集合中
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        if (oldValue != null) {
            SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(oldValue);
            mSoftCacheMap.put(key, softReference);
        }
    }

    public Map<String, SoftReference<Bitmap>> getCacheMap() {
        return mSoftCacheMap;
    }

}
