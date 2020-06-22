package com.example.score.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.score.util.PicCacheUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/***
 * 本地缓存
 */
public class LocalCache {
    /**
     * 从本地读取图片
     *
     * @param url
     */
    public Bitmap getBitmapFromLocal(String url) {
        String fileName = null;//把图片的url当做文件名,并进行MD5加密
        try {
            //fileName = MD5Tools.MD5(url);
            File file = new File(PicCacheUtil.getInstance().CACHE_PATH, url);

            if(file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从网络获取图片后,保存至本地缓存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        try {
            File file = new File(PicCacheUtil.getInstance().CACHE_PATH,url);

            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
